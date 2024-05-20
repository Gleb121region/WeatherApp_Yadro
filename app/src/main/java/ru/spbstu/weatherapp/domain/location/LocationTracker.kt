package ru.spbstu.weatherapp.domain.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
    suspend fun getCityName(lat: Double, long: Double): String?
}