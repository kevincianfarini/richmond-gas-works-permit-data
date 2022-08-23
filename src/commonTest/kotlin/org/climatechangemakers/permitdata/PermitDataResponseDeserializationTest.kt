package org.climatechangemakers.permitdata

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class PermitDataResponseDeserializationTest {

  private val json = Json { ignoreUnknownKeys = true }

  @Test fun `deserializes single entity correctly`() {
    val jsonString = """
      |{
      |    "CaseId": "44f4e52d-c822-4415-a865-e1c31d7ddef0",
      |    "CaseNumber": "GASC-075285-2020",
      |    "CaseTypeId": "adc4a303-fd97-48a7-8908-ac6d14e76204",
      |    "CaseType": "Gas (C) - New",
      |    "CaseWorkclassId": "417a924b-4e3d-4bdb-80c1-f0da0e3489b0",
      |    "CaseWorkclass": "New",
      |    "CaseStatusId": "fb2536ab-0752-4338-9c03-cefca1c35c3f",
      |    "CaseStatus": "Issued",
      |    "ProjectName": "",
      |    "IssueDate": "2020-10-05T04:00:00Z",
      |    "ApplyDate": "2020-10-05T17:20:03.863Z",
      |    "ExpireDate": "2023-10-05T04:00:00Z",
      |    "CompleteDate": null,
      |    "FinalDate": null,
      |    "RequestDate": null,
      |    "ScheduleDate": null,
      |    "StartDate": null,
      |    "ExpectedEndDate": null,
      |    "Address": {
      |        "CountryTypeId": 0,
      |        "CountryTypeName": "US",
      |        "CountryName": null,
      |        "StreetTypeName": "St",
      |        "PreDirection": "W",
      |        "PostDirection": "",
      |        "AddressLine1": "4905",
      |        "AddressLine2": "Broad ",
      |        "AddressLine3": "",
      |        "AddressTypeName": "Site Address",
      |        "UnitOrSuite": "STE B",
      |        "City": "Richmond",
      |        "StateName": "VA",
      |        "ProvinceName": "",
      |        "RuralRoute": "",
      |        "POBox": "",
      |        "Station": "",
      |        "CompSite": "",
      |        "ATTN": "",
      |        "PostalCode": "23230",
      |        "IsMain": true,
      |        "FullAddress": "4905 W Broad  St STE B Richmond VA 23230"
      |    },
      |    "ModuleName": 2,
      |    "AddressDisplay": "4905 W Broad St Unit: STE B Richmond VA 23230",
      |    "MainParcel": "",
      |    "Description": "",
      |    "DBA": null,
      |    "LicenseYear": null,
      |    "CompanyName": null,
      |    "CompanyTypeName": null,
      |    "BusinessTypeName": null,
      |    "TaxID": null,
      |    "OpenedDate": null,
      |    "ClosedDate": null,
      |    "LastAuditDate": null,
      |    "HolderCompanyName": null,
      |    "HolderFirstName": null,
      |    "HolderLastName": null,
      |    "HolderMiddleName": null,
      |    "BusinessId": null,
      |    "BusinessStatus": null,
      |    "Highlights": null
      |}
    """.trimMargin()

    assertEquals(
      expected = PermitResult(
        caseTypeId = "adc4a303-fd97-48a7-8908-ac6d14e76204",
        caseWorkClassId = "417a924b-4e3d-4bdb-80c1-f0da0e3489b0",
        status = "Issued",
        issuedDate = "2020-10-05T04:00:00Z",
        applyDate = "2020-10-05T17:20:03.863Z",
        expireDate = "2023-10-05T04:00:00Z",
        completeDate = null,
        finalDate = null,
        requestDate = null,
        address = Address(
          city = "Richmond",
          postalCode = "23230",
          fullAddress = "4905 W Broad  St STE B Richmond VA 23230",
        )
      ),
      actual = json.decodeFromString(PermitResult.serializer(), jsonString)
    )
  }
}