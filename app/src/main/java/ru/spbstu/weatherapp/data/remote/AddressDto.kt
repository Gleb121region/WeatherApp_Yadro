package ru.spbstu.weatherapp.data.remote

import com.squareup.moshi.Json

data class AddressDto(
    @field:Json(name = "ISO3166-2-lvl15") val lvl15: String,
    @field:Json(name = "ISO3166-2-lvl4") val lvl4: String,
    val city: String,
    @field:Json(name = "city_district") val cityDistrict: String,
    val country: String,
    @field:Json(name = "country_code") val countryCode: String,
    val postcode: String,
    val region: String,
    val road: String,
    val state: String
)