package org.climatechangemakers.permitdata

import io.ktor.client.*
import io.ktor.client.engine.curl.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.core.*
import kotlinx.serialization.json.Json

class PermitService(
  private val client: HttpClient,
  private val json: Json
) : Closeable {

  suspend fun getPermitData(permitType: PermitType, issueDateToString: String?): PermitDataResponse {
    val request = json.encodeToString(
      serializer = PermitDataRequest.serializer(),
      value = PermitDataRequest(permitType, issueDateToString),
    )
    val response = client.post("https://energov.richmondgov.com/EnerGov_Prod/selfservice/api/energov/search/search") {
      timeout { requestTimeoutMillis = Long.MAX_VALUE }
      headers {
        append("Accept", "application/json")
        append("Content-Type", "application/json;charset=utf-8")
        append("tenantId", "1")
        append("tenantName", "richmondvaprod")
        append("Tyler-TenantUrl", "richmondvaprod")
        append("Tyler-Tenant-Culture", "en-US")
        append("Origin", "https://energov.richmondgov.com")
        append("Referer", "https://energov.richmondgov.com/EnerGov_Prod/SelfService/richmondvaprod")
        append("Sec-Fetch-Mode", "cors")
        append("Sec-Fetch-Site", "same-origin")
      }
      setBody(request)
    }

    val body = response.bodyAsText()
    return json.decodeFromString(
      deserializer = PermitDataResponse.serializer(),
      string = body,
    )
  }

  override fun close() = client.close()
}