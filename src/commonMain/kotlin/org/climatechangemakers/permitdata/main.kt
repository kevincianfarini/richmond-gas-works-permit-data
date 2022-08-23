package org.climatechangemakers.permitdata

import io.ktor.utils.io.core.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

fun main() {
  runBlocking {
    val json = Json { ignoreUnknownKeys = true }

    PermitService(json).use { service ->
      PermitType.values().map { type ->
        println("${type.permitName}: ${service.getPermitData(type).result.numRecords} permits collected.")
      }
    }

    println("finished use.")
  }
}