package org.climatechangemakers.permitdata

enum class PermitType(
  val reason: PermitReason,
  val installationDestination: InstallationDestination,
  val caseTypeId: String,
  val caseWorkClassId: String
) {
  GasCommercialAddition(
    reason = PermitReason.Addition,
    installationDestination = InstallationDestination.Commercial,
    caseTypeId = "adc4a303-fd97-48a7-8908-ac6d14e76204",
    caseWorkClassId = "8e274d94-bc4c-45ce-b61d-bc17136abfc4",
  ),
  GasCommercialNew(
    reason = PermitReason.New,
    installationDestination = InstallationDestination.Commercial,
    caseTypeId = "adc4a303-fd97-48a7-8908-ac6d14e76204",
    caseWorkClassId = "417a924b-4e3d-4bdb-80c1-f0da0e3489b0",
  ),
  GasCommercialMainExtension(
    reason = PermitReason.MainExtension,
    installationDestination = InstallationDestination.Commercial,
    caseTypeId = "adc4a303-fd97-48a7-8908-ac6d14e76204",
    caseWorkClassId = "55c62b97-c08a-48dd-9176-06d3fdf3ef54",
  ),
  GasCommercialUpgrade(
    reason = PermitReason.Upgrade,
    installationDestination = InstallationDestination.Commercial,
    caseTypeId = "adc4a303-fd97-48a7-8908-ac6d14e76204",
    caseWorkClassId = "d737865d-8f41-4d95-b05c-7d646044c818",
  ),
  GasResidentialMainExtension(
    reason = PermitReason.MainExtension,
    installationDestination = InstallationDestination.Residential,
    caseTypeId = "4b52b5cb-4d97-4a70-a61c-93a2f0906493",
    caseWorkClassId = "55c62b97-c08a-48dd-9176-06d3fdf3ef54",
  ),
  GasResidentialNew(
    reason = PermitReason.New,
    installationDestination = InstallationDestination.Residential,
    caseTypeId = "4b52b5cb-4d97-4a70-a61c-93a2f0906493",
    caseWorkClassId = "417a924b-4e3d-4bdb-80c1-f0da0e3489b0",
  ),
  GasResidentialUpgrade(
    reason = PermitReason.Upgrade,
    installationDestination = InstallationDestination.Residential,
    caseTypeId = "4b52b5cb-4d97-4a70-a61c-93a2f0906493",
    caseWorkClassId = "d737865d-8f41-4d95-b05c-7d646044c818",
  ),
  GasPipingCommercialAddition(
    reason = PermitReason.Addition,
    installationDestination = InstallationDestination.Commercial,
    caseTypeId = "58bc902f-dfe7-4716-b8ad-9fdc91ec64b2",
    caseWorkClassId = "8cb17d8f-3c86-4ec2-8279-d9739e5049d5",
  ),
  GasPipingCommericalHeavyRemodel(
    reason = PermitReason.HeavyRemodel,
    installationDestination = InstallationDestination.Commercial,
    caseTypeId = "58bc902f-dfe7-4716-b8ad-9fdc91ec64b2",
    caseWorkClassId = "06ea6722-bf1e-4560-b440-9e782369c7cd",
  ),
  GasPipingCommericalLightRemodel(
    reason = PermitReason.LightRemodel,
    installationDestination = InstallationDestination.Commercial,
    caseTypeId = "58bc902f-dfe7-4716-b8ad-9fdc91ec64b2",
    caseWorkClassId = "f16e4fc2-7501-44e5-94da-1467f027d358",
  ),
  GasPipingCommercialNew(
    reason = PermitReason.New,
    installationDestination = InstallationDestination.Commercial,
    caseTypeId = "58bc902f-dfe7-4716-b8ad-9fdc91ec64b2",
    caseWorkClassId = "417a924b-4e3d-4bdb-80c1-f0da0e3489b0",
  ),
  GasPipingCommercialRepairReplace(
    reason = PermitReason.RepairReplace,
    installationDestination = InstallationDestination.Commercial,
    caseTypeId = "58bc902f-dfe7-4716-b8ad-9fdc91ec64b2",
    caseWorkClassId = "f92ace43-b381-471e-9263-108018db5502",
  ),
  GasPipingResidentialAccessory(
    reason = PermitReason.Accessory,
    installationDestination = InstallationDestination.Residential,
    caseTypeId = "cfa18978-9cf7-47f4-91d9-4663f1481258",
    caseWorkClassId = "363d174a-e64e-49eb-a076-3f3707840690",
  ),
  GasPipingResidentialAddition(
    reason = PermitReason.Addition,
    installationDestination = InstallationDestination.Residential,
    caseTypeId = "cfa18978-9cf7-47f4-91d9-4663f1481258",
    caseWorkClassId = "8cb17d8f-3c86-4ec2-8279-d9739e5049d5",
  ),
  GasPipingResidentialHeavyRemodel(
    reason = PermitReason.HeavyRemodel,
    installationDestination = InstallationDestination.Residential,
    caseTypeId = "cfa18978-9cf7-47f4-91d9-4663f1481258",
    caseWorkClassId = "06ea6722-bf1e-4560-b440-9e782369c7cd",
  ),
  GasPipingResidentialLightRemodel(
    reason = PermitReason.LightRemodel,
    installationDestination = InstallationDestination.Residential,
    caseTypeId = "cfa18978-9cf7-47f4-91d9-4663f1481258",
    caseWorkClassId = "f16e4fc2-7501-44e5-94da-1467f027d358",
  ),
  GasPipingResidentialNew(
    reason = PermitReason.New,
    installationDestination = InstallationDestination.Residential,
    caseTypeId = "cfa18978-9cf7-47f4-91d9-4663f1481258",
    caseWorkClassId = "417a924b-4e3d-4bdb-80c1-f0da0e3489b0",
  ),
  GasPipingResidentialRepairReplace(
    reason = PermitReason.RepairReplace,
    installationDestination = InstallationDestination.Residential,
    caseTypeId = "cfa18978-9cf7-47f4-91d9-4663f1481258",
    caseWorkClassId = "f92ace43-b381-471e-9263-108018db5502",
  ),
  GasResidentialAddition(
    reason = PermitReason.Addition,
    installationDestination = InstallationDestination.Residential,
    caseTypeId = "4b52b5cb-4d97-4a70-a61c-93a2f0906493",
    caseWorkClassId = "8e274d94-bc4c-45ce-b61d-bc17136abfc4",
  );

  companion object {
    fun parse(caseTypeId: String, caseWorkClassId: String) = values().first { type ->
      type.caseTypeId == caseTypeId && type.caseWorkClassId == caseWorkClassId
    }
  }
}