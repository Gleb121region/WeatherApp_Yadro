package ru.spbstu.weatherapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.spbstu.weatherapp.data.repository.OpenStreetMapRepositoryImpl
import ru.spbstu.weatherapp.data.repository.WeatherRepositoryImpl
import ru.spbstu.weatherapp.domain.repository.OpenStreetMapRepository
import ru.spbstu.weatherapp.domain.repository.WeatherRepository
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindOpenStreetMapRepository(
        openStreetMapRepositoryImpl: OpenStreetMapRepositoryImpl
    ): OpenStreetMapRepository
}