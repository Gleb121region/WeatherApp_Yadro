package ru.spbstu.weatherapp.data.mappers

import ru.spbstu.weatherapp.data.remote.OpenStreetMapDto
import ru.spbstu.weatherapp.domain.map.City

fun OpenStreetMapDto.mapToCity(): City = City(this.address.city)
