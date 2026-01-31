package com.example.weatherapp.data.model

data class WeatherResponse(
    val current_weather: CurrentWeather,
    val hourly: HourlyData,
    val daily: DailyForecast
)

data class CurrentWeather(
    val temperature: Double,
    val windspeed: Double,
    val time: String,
    val weathercode: Int
)

data class HourlyData(
    val relativehumidity_2m: List<Int>
)

data class DailyForecast(
    val time: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>
)
