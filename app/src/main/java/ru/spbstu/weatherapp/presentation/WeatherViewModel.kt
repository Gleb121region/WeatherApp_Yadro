package ru.spbstu.weatherapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.spbstu.weatherapp.domain.location.LocationTracker
import ru.spbstu.weatherapp.domain.repository.OpenStreetMapRepository
import ru.spbstu.weatherapp.domain.repository.WeatherRepository
import ru.spbstu.weatherapp.domain.util.Resource
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val openStreetMapRepository: OpenStreetMapRepository,
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                val cityNameResource = openStreetMapRepository.getWeatherData(location.latitude, location.longitude)
                if (cityNameResource is Resource.Success) {
                    val cityName = cityNameResource.data?.city
                    val weatherResult = weatherRepository.getWeatherData(location.latitude, location.longitude)
                    state = when (weatherResult) {
                        is Resource.Success -> {
                            state.copy(
                                weatherInfo = weatherResult.data,
                                cityName = cityName,
                                isLoading = false,
                                error = null
                            )
                        }
                        is Resource.Error -> {
                            state.copy(
                                weatherInfo = null,
                                cityName = null,
                                isLoading = false,
                                error = weatherResult.message
                            )
                        }
                    }
                } else {
                    state = state.copy(
                        isLoading = false,
                        error = "Couldn't retrieve city name."
                    )
                }
            } else {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }
}