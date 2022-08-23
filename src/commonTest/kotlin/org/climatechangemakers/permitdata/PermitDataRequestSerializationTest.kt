package org.climatechangemakers.permitdata

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class PermitDataRequestSerializationTest {

  private val json = Json {
    prettyPrint = true
    prettyPrintIndent = "    "
  }

  @Test fun `serializes correctly`() {

    val request = PermitDataRequest(PermitType.GasCommercialNew)
    assertEquals(
      expected = """
        |{
        |    "SearchModule": 2,
        |    "PermitCriteria": {
        |        "PermitNumber": null,
        |        "PermitTypeId": "adc4a303-fd97-48a7-8908-ac6d14e76204_417a924b-4e3d-4bdb-80c1-f0da0e3489b0",
        |        "PermitWorkclassId": null,
        |        "PermitStatusId": "none",
        |        "ProjectName": null,
        |        "IssueDateFrom": null,
        |        "IssueDateTo": null,
        |        "Address": null,
        |        "Description": null,
        |        "ExpireDateFrom": null,
        |        "ExpireDateTo": null,
        |        "FinalDateFrom": null,
        |        "FinalDateTo": null,
        |        "ApplyDateFrom": null,
        |        "ApplyDateTo": null,
        |        "SearchMainAddress": false,
        |        "ContactId": null,
        |        "TypeId": null,
        |        "WorkClassIds": null,
        |        "ParcelNumber": null,
        |        "ExcludeCases": null,
        |        "EnableDescriptionSearch": false,
        |        "PageNumber": 1,
        |        "PageSize": 100000,
        |        "SortBy": "relevance",
        |        "SortAscending": false
        |    },
        |    "PlanCriteria": {
        |    },
        |    "InspectionCriteria": {
        |    },
        |    "CodeCaseCriteria": {
        |    },
        |    "RequestCriteria": {
        |    },
        |    "BusinessLicenseCriteria": {
        |    },
        |    "ProfessionalLicenseCriteria": {
        |    },
        |    "LicenseCriteria": {
        |    },
        |    "ProjectCriteria": {
        |    }
        |}
      """.trimMargin(),
      actual = json.encodeToString(
        serializer = PermitDataRequest.serializer(),
        value = request,
      )
    )
  }
}