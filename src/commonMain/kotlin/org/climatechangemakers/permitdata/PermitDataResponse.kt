package org.climatechangemakers.permitdata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable class PermitDataResponse(
  @SerialName("Result") val result: PermitDataResponseResult,
)

@Serializable class PermitDataResponseResult(
  @SerialName("TotalPages") val totalPeges: Int,
  @SerialName("EntityResults") val permitResults: List<PermitResult>,
)

@Serializable data class PermitResult(
  @SerialName("CaseTypeId") private val caseTypeId: String,
  @SerialName("CaseWorkclassId") private val caseWorkClassId: String,
  @SerialName("Address") val address: Address?,
  @SerialName("CaseStatus") val status: String,
  @SerialName("IssueDate") val issuedDate: String?,
  @SerialName("ApplyDate") val applyDate: String?,
  @SerialName("ExpireDate") val expireDate: String?,
  @SerialName("CompleteDate") val completeDate: String?,
  @SerialName("FinalDate") val finalDate: String?,
  @SerialName("RequestDate") val requestDate: String?,
) {
  val permitType get() = PermitType.parse(caseTypeId, caseWorkClassId)
}

@Serializable data class Address(
  @SerialName("City") val city: String?,
  @SerialName("PostalCode") val postalCode: String,
  @SerialName("FullAddress") val fullAddress: String,
  @SerialName("PreDirection") private val preDirection: String,
  @SerialName("PostDirection") private val postDirection: String,
  @SerialName("StreetTypeName") private val streetType: String,
  @SerialName("AddressLine1") private val addressLine1: String,
  @SerialName("AddressLine2") private val addressLine2: String,
) {

  val streetAddress = "$addressLine1 $preDirection $addressLine2 $postDirection $streetType"
}