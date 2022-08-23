package org.climatechangemakers.permitdata

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable class PermitDataRequest private constructor(
  @SerialName("SearchModule") private val searchModule: Int,
  @SerialName("PermitCriteria") private val permitCritera: PermitCriteria,

  // The following are keys necessary for the backend service not to choke. They're empty.
  @SerialName("PlanCriteria") private val planCriteria: Map<String, String>,
  @SerialName("InspectionCriteria") private val inspectionCritera: Map<String, String>,
  @SerialName("CodeCaseCriteria") private val codeCaseCriteria: Map<String, String>,
  @SerialName("RequestCriteria") private val requestCriteria: Map<String, String>,
  @SerialName("BusinessLicenseCriteria") private val businessLicenseCriteria: Map<String, String>,
  @SerialName("ProfessionalLicenseCriteria") private val professionalLicenseCriteria: Map<String, String>,
  @SerialName("LicenseCriteria") private val licenseCriteria: Map<String, String>,
  @SerialName("ProjectCriteria") private val projctCriteria: Map<String, String>,
) {

  constructor(permitType: PermitType) : this(
    searchModule = 2,
    permitCritera = PermitCriteria(permitType, 100_000),
    planCriteria = emptyMap(),
    inspectionCritera = emptyMap(),
    codeCaseCriteria = emptyMap(),
    requestCriteria = emptyMap(),
    businessLicenseCriteria = emptyMap(),
    professionalLicenseCriteria = emptyMap(),
    licenseCriteria = emptyMap(),
    projctCriteria = emptyMap(),
  )
}

@Serializable private class PermitCriteria private constructor(
  @SerialName("PermitNumber") private val permitNumber: String?,

  @SerialName("PermitTypeId") @Serializable(with = PermitTypeIdSerializer::class)
  private val permitTypeId: PermitType,

  @SerialName("PermitWorkclassId") private val permitWorkClassId: String?,
  @SerialName("PermitStatusId") private val permitStatusId: String,
  @SerialName("ProjectName") private val projectName: String?,
  @SerialName("IssueDateFrom") private val issueDateFrom: String?,
  @SerialName("IssueDateTo") private val issueDateTo: String?,
  @SerialName("Address") private val address: String?,
  @SerialName("Description") private val description: String?,
  @SerialName("ExpireDateFrom") private val expireDateFrom: String?,
  @SerialName("ExpireDateTo") private val expireDateTo: String?,
  @SerialName("FinalDateFrom") private val finalDateFrom: String?,
  @SerialName("FinalDateTo") private val finalDateTo: String?,
  @SerialName("ApplyDateFrom") private val applyDateFrom: String?,
  @SerialName("ApplyDateTo") private val applyDateTo: String?,
  @SerialName("SearchMainAddress") private val searchMainAddress: Boolean,
  @SerialName("ContactId") private val contactId: String?,
  @SerialName("TypeId") private val typeId: String?,
  @SerialName("WorkClassIds") private val workClassIds: String?,
  @SerialName("ParcelNumber") private val parcelNumber: String?,
  @SerialName("ExcludeCases") private val excludeCases: String?,
  @SerialName("EnableDescriptionSearch") private val enableDescriptionSearch: Boolean,
  @SerialName("PageNumber") private val pageNumber: Int,
  @SerialName("PageSize") private val pageSize: Int,
  @SerialName("SortBy") private val sortBy: String,
  @SerialName("SortAscending") private val sortAscending: Boolean,
) {

  constructor(permitTypeId: PermitType, pageSize: Int) : this(
    permitNumber = null,
    permitTypeId = permitTypeId,
    permitWorkClassId = null,
    permitStatusId = "none",
    projectName = null,
    issueDateFrom = null,
    issueDateTo = null,
    address = null,
    description = null,
    expireDateFrom = null,
    expireDateTo = null,
    finalDateFrom = null,
    finalDateTo = null,
    applyDateFrom = null,
    applyDateTo = null,
    searchMainAddress = false,
    contactId = null,
    typeId = null,
    workClassIds = null,
    parcelNumber = null,
    excludeCases = null,
    enableDescriptionSearch = false,
    pageNumber = 1,
    pageSize = pageSize,
    sortBy = "relevance",
    sortAscending = false,
  )
}

private object PermitTypeIdSerializer : KSerializer<PermitType> {
  override val descriptor: SerialDescriptor get() = PrimitiveSerialDescriptor(
    serialName = "PermitTypeId",
    kind = PrimitiveKind.STRING,
  )

  override fun deserialize(decoder: Decoder): PermitType {
    TODO("Not yet implemented")
  }

  override fun serialize(encoder: Encoder, value: PermitType) {
    encoder.encodeString("${value.caseTypeId}_${value.caseWorkClassId}")
  }
}