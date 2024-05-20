package ru.spbstu.weatherapp.data.remote

import com.squareup.moshi.Json

data class OpenStreetMapDto(
    val address: AddressDto,
    @field:Json(name = "addresstype") val addressType: String,
    @field:Json(name = "boundingbox") val boundingBox: List<String>,
    val category: String,
    @field:Json(name = "display_name") val displayName: String,
    val importance: Double,
    val lat: String,
    val licence: String,
    val lon: String,
    val name: String,
    @field:Json(name = "osm_id") val osmId: Int,
    @field:Json(name = "osm_type") val osmType: String,
    @field:Json(name = "place_id") val placeId: Int,
    @field:Json(name = "place_rank") val placeRank: Int,
    val type: String
)