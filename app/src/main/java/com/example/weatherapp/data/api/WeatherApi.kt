package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,

        @Query("current_weather") current: Boolean = true,

        @Query("hourly") hourly: String = "relativehumidity_2m",

        @Query("daily") daily: String =
            "temperature_2m_max,temperature_2m_min",

        @Query("timezone") timezone: String = "auto"
    ): WeatherResponse
}
