package org.climatechangemakers.permitdata

import kotlinx.serialization.Serializable

@Serializable class GeocodeResponse(
  val lat: String,
  val lon: String,
  val address: GeocodedAddress,
)

@Serializable class GeocodedAddress(
  val city: String?,
  val postcode: String?,
  val county: String?
)