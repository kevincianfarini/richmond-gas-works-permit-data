package org.climatechangemakers.permitdata

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app.cash.sqldelight.driver.native.wrapConnection
import co.touchlab.sqliter.DatabaseConfiguration
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.mordant.animation.textAnimation
import com.github.ajalt.mordant.terminal.Terminal
import io.ktor.client.*
import io.ktor.client.engine.curl.*
import io.ktor.client.plugins.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.climatechangemakers.permitdata.database.Database
import org.climatechangemakers.permitdata.database.Permit
import org.climatechangemakers.permitdata.database.PermitQueries

fun main(args: Array<String>) = Parse().main(args)

class Parse : CliktCommand() {

  private val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
  }

  private val databasePath: String by argument(
    name = "Database File",
    help = "The path to which the SQLite database file will be created.",
  )

  private val nominatimUrl: String by argument(
    name = "Nominatim URL",
    help = "The URL to a nominatim instance which performs geocoding.",
  )

  override fun run() = runBlocking {
    val database = createDb(databasePath)
    val terminal = Terminal()
    val insertProgress = terminal.textAnimation<Int> { inserted ->
      "Inserted $inserted permits."
    }.also { it.update(0) }

    val httpClient = HttpClient(Curl) {
      install(HttpTimeout)
    }

    httpClient.use { client ->
      createGeocodingFlow(
        source = createPermitDataFlow(client),
        client = client,
      ).collectIndexed { index, (record, geocode) ->
        database.permitQueries.insertPermit(record, geocode)
        insertProgress.update(index + 1)
      }
    }
  }

  private fun createGeocodingFlow(
    source: Flow<PermitResult>,
    client: HttpClient,
  ): Flow<Pair<PermitResult, GeocodeResponse?>> {
    val service = GeocodeService(nominatimUrl, client, json)
    return source.map { record ->
      val geocodeResult = record.address?.let { address ->
        service.search(
          address.streetAddress,
          address.postalCode,
        )
      }

      Pair(record, geocodeResult)
    }
  }

  private fun createPermitDataFlow(client: HttpClient): Flow<PermitResult> = channelFlow {
    PermitService(client, json).use { service ->
      coroutineScope {
        PermitType.values().forEach { type ->
          launch {
            service.getPermitData(type).result.permitResults.forEach { permit ->
              send(permit)
            }
          }
        }
      }
    }
  }.buffer(Channel.RENDEZVOUS)

  private fun createDb(path: String): Database = Database(
    driver = createSqlDriver(path, Database.Schema),
    permitAdapter = Permit.Adapter(
      reasonAdapter = EnumColumnAdapter(),
      destinationAdapter = EnumColumnAdapter(),
    ),
  )

  private fun createSqlDriver(
    path: String,
    schema: SqlSchema,
  ): SqlDriver {
    val splitPath = path.split("/")
    return NativeSqliteDriver(
      configuration = DatabaseConfiguration(
        name = splitPath.last(),
        version = schema.version,
        create = { connection -> wrapConnection(connection, schema::create) },
        upgrade = { connection, old, new ->
          wrapConnection(connection) { schema.migrate(it, old, new) }
        },
        extendedConfig = DatabaseConfiguration.Extended(
          basePath = splitPath.subList(0, splitPath.lastIndex - 1).joinToString("/"),
        )
      )
    )
  }
}

private fun PermitQueries.insertPermit(
  record: PermitResult,
  geocode: GeocodeResponse?,
) {
  insert(
    status = record.status,
    issuedDate = record.issuedDate,
    applicationDate = record.applyDate,
    expirationDate = record.expireDate,
    completionDate = record.completeDate,
    finalDate = record.finalDate,
    requestDate = record.requestDate,
    sourceCity = record.address?.city,
    sourcePostCode = record.address?.postalCode,
    sourceFullAddress = record.address?.fullAddress,
    geocodedCity = geocode?.address?.county ?: geocode?.address?.city,
    geocodedPostCode = geocode?.address?.postcode,
    geocodedLatitude = geocode?.lat?.toDouble(),
    geocodedLongitude = geocode?.lon?.toDouble(),
    reason = record.permitType.reason,
    destination = record.permitType.installationDestination,
  )
}