package org.climatechangemakers.permitdata

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.mordant.animation.textAnimation
import com.github.ajalt.mordant.terminal.Terminal
import io.ktor.utils.io.core.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.climatechangemakers.permitdata.database.Database
import org.climatechangemakers.permitdata.database.Permit
import org.climatechangemakers.permitdata.database.PermitQueries

fun main(args: Array<String>) = Parse().main(args)

class Parse : CliktCommand() {

  private val json = Json { ignoreUnknownKeys = true }
  private val databasePath: String by argument(
    name = "Database File",
    help = "The path to which the SQLite database file will be created.",
  )

  override fun run() = runBlocking {
    val database = createDb(databasePath)
    val progress = Terminal().textAnimation<Int> { inserted ->
      "Inserted $inserted permits."
    }.also { it.update(0) }

      createPermitDataFlow().collectIndexed { index, record ->
        database.permitQueries.insertPermit(record)
        progress.update(index + 1)
      }
  }

  private fun createPermitDataFlow(): Flow<PermitResult> = channelFlow {
    PermitService(json).use { service ->
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
  }.buffer(Channel.UNLIMITED)

  private fun createDb(path: String): Database = Database(
    driver = NativeSqliteDriver(
      schema = Database.Schema,
      name = path,
    ),
    permitAdapter = Permit.Adapter(
      reasonAdapter = EnumColumnAdapter(),
      destinationAdapter = EnumColumnAdapter(),
    ),
  )
}

private fun PermitQueries.insertPermit(record: PermitResult) {
  insert(
    status = record.status,
    issuedDate = record.issuedDate,
    applicationDate = record.applyDate,
    expirationDate = record.expireDate,
    completionDate = record.completeDate,
    finalDate = record.finalDate,
    requestDate = record.requestDate,
    city = record.address?.city,
    postalCode = record.address?.postalCode,
    fullAddress = record.address?.fullAddress,
    reason = record.permitType.reason,
    destination = record.permitType.installationDestination,
  )
}