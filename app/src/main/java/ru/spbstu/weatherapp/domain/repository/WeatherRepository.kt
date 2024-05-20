package ru.spbstu.weatherapp.domain.repository

import ru.spbstu.weatherapp.domain.util.Resource
import ru.spbstu.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}