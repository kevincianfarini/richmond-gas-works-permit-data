package org.climatechangemakers.permitdata

import io.ktor.utils.io.core.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

fun main() = runBlocking {
  collectData()
}

private suspend fun collectData() = coroutineScope {
  val json = Json { ignoreUnknownKeys = true }
  val resultChannel = Channel<PermitDataResponse>(Channel.BUFFERED)

  launch {
    for (result in resultChannel) {
      val foo = result.result.permitResults.first().permitType
      println("${foo.permitName} :: ${foo.installationDestination} :: ${result.result.numRecords} collected.")
    }
  }

  PermitService(json).use { service ->
    coroutineScope {
        PermitType.values().map { type ->
          launch {
            resultChannel.send(service.getPermitData(type))
          }
        }
    }
    resultChannel.close()
  }

  println("finished use.")
}