package ru.spbstu.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface OpenStreetMapApi {
    @Headers(
        "Content-Type: application/json"
    )
    @GET("reverse?format=jsonv2")
    suspend fun reverseGeoPointToAddress(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
    ): OpenStreetMapDto
}