package org.climatechangemakers.permitdata

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class GeocodeService(
  private val baseUrl: String,
  private val client: HttpClient,
  private val json: Json,
) {

  suspend fun search(
    streetAddress: String,
    postcode: String,
  ): GeocodeResponse? {
    val response = client.get("$baseUrl/search") {
      parameter("street", streetAddress)
      parameter("postalcode", postcode)
      parameter("addressdetails", 1)
    }

    return json.decodeFromString(
      deserializer = ListSerializer(GeocodeResponse.serializer()),
      string = response.bodyAsText(),
    ).firstOrNull()
  }
}