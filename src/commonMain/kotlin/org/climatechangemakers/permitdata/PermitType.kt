package org.climatechangemakers.permitdata

enum class PermitType(
  val permitName: String,
  val caseTypeId: String,
  val caseWorkClassId: String
) {
  GasCommercialAddition(
    permitName = "Gas (C) - Addition",
    caseTypeId = "adc4a303-fd97-48a7-8908-ac6d14e76204",
    caseWorkClassId = "8e274d94-bc4c-45ce-b61d-bc17136abfc4"
  ),
  GasCommercialNew(
    permitName = "Gas (C) - New",
    caseTypeId = "adc4a303-fd97-48a7-8908-ac6d14e76204",
    caseWorkClassId = "417a924b-4e3d-4bdb-80c1-f0da0e3489b0"
  ),
  GasCommercialMainExtension(
    permitName = "Gas (C) - Main Extension",
    caseTypeId = "adc4a303-fd97-48a7-8908-ac6d14e76204",
    caseWorkClassId = "55c62b97-c08a-48dd-9176-06d3fdf3ef54"
  ),
  GasCommercialUpgrade(
    permitName = "Gas (C) - Upgrade",
    caseTypeId = "adc4a303-fd97-48a7-8908-ac6d14e76204",
    caseWorkClassId = "d737865d-8f41-4d95-b05c-7d646044c818"
  ),
  GasResidentialMainExtension(
    permitName = "Gas (R) - Main Extension",
    caseTypeId = "4b52b5cb-4d97-4a70-a61c-93a2f0906493",
    caseWorkClassId = "55c62b97-c08a-48dd-9176-06d3fdf3ef54"
  ),
  GasResidentialNew(
    permitName = "Gas (R) - New",
    caseTypeId = "4b52b5cb-4d97-4a70-a61c-93a2f0906493",
    caseWorkClassId = "417a924b-4e3d-4bdb-80c1-f0da0e3489b0"
  ),
  GasResidentialUpgrade(
    permitName = "Gas (R) - Upgrade",
    caseTypeId = "4b52b5cb-4d97-4a70-a61c-93a2f0906493",
    caseWorkClassId = "d737865d-8f41-4d95-b05c-7d646044c818"
  )
}