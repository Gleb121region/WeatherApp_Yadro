package ru.spbstu.weatherapp.domain.repository

import ru.spbstu.weatherapp.domain.map.City
import ru.spbstu.weatherapp.domain.util.Resource

interface OpenStreetMapRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<City>
}