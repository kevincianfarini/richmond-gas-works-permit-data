package org.climatechangemakers.permitdata

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.github.ajalt.mordant.animation.textAnimation
import com.github.ajalt.mordant.terminal.Terminal
import io.ktor.utils.io.core.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.climatechangemakers.permitdata.database.Database
import org.climatechangemakers.permitdata.database.Permit

fun main() = runBlocking {
  collectData()
}

private suspend fun collectData() {
  val json = Json { ignoreUnknownKeys = true }
  val database = Database(
    driver = NativeSqliteDriver(
      schema = Database.Schema,
      name = "db.db",
    ),
    permitAdapter = Permit.Adapter(
      reasonAdapter = EnumColumnAdapter(),
      destinationAdapter = EnumColumnAdapter(),
    ),
  )
  val terminal = Terminal()
  val progress = terminal.textAnimation<Int> { inserted ->
    "Inserted $inserted permits."
  }.also { it.update(0) }


  channelFlow {
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
  }.buffer(Channel.UNLIMITED).collectIndexed { index, record ->
    database.permitQueries.insert(
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
    progress.update(index + 1)
  }
}