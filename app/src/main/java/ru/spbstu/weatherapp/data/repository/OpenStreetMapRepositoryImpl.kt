package ru.spbstu.weatherapp.data.repository

import ru.spbstu.weatherapp.data.mappers.mapToCity
import ru.spbstu.weatherapp.data.remote.OpenStreetMapApi
import ru.spbstu.weatherapp.domain.map.City
import ru.spbstu.weatherapp.domain.repository.OpenStreetMapRepository
import ru.spbstu.weatherapp.domain.util.Resource
import javax.inject.Inject

class OpenStreetMapRepositoryImpl @Inject constructor(
    private val api: OpenStreetMapApi
) : OpenStreetMapRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<City> {
        return try {
            Resource.Success(
                api.reverseGeoPointToAddress(
                    lat = lat,
                    lon = long
                ).mapToCity()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}