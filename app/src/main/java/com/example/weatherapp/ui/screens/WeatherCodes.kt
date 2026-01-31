package com.example.weatherapp.ui.screens

fun weatherCondition(code: Int): String {
    return when (code) {
        0 -> "Clear sky ☀️"
        1, 2 -> "Partly cloudy ⛅"
        3 -> "Overcast ☁️"
        45, 48 -> "Fog 🌫"
        51, 53, 55 -> "Drizzle 🌦"
        61, 63, 65 -> "Rain 🌧"
        71, 73, 75 -> "Snow ❄️"
        95 -> "Thunderstorm ⛈"
        else -> "Unknown weather"
    }
}
