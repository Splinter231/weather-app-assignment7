package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.viewmodel.WeatherViewModel
import kotlin.math.roundToInt

@Composable
fun WeatherScreen(
    vm: WeatherViewModel,
    unit: String,
    onBack: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val weather by vm.weather.collectAsState()
    val cityName by vm.cityName.collectAsState()
    val offline by vm.offline.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        // ✅ Top buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(onClick = { onBack() }) {
                Text("⬅ Back")
            }

            Button(onClick = { onOpenSettings() }) {
                Text("⚙ Settings")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ✅ Offline Label
        if (offline) {
            Text(
                text = "⚠ OFFLINE MODE: Showing cached weather for $cityName",
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        // ✅ Title
        Text(
            text = "📍 Weather in $cityName",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        weather?.let { data ->

            val temp = convertTemp(data.current_weather.temperature, unit)

            val conditionText =
                weatherCondition(data.current_weather.weathercode)

            val humidity =
                data.hourly.relativehumidity_2m.firstOrNull() ?: 0

            // ✅ Required fields
            Text("🌡 Temperature: ${temp.roundToInt()}°$unit")
            Text("🌤 Condition: $conditionText")
            Text("💧 Humidity: $humidity%")
            Text("💨 Wind: ${data.current_weather.windspeed} km/h")
            Text("🕒 Updated: ${data.current_weather.time}")

            Spacer(modifier = Modifier.height(25.dp))

            // ✅ Forecast
            Text(
                text = "📅 3-Day Forecast",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn {
                itemsIndexed(data.daily.time.take(3)) { index, day ->

                    val maxT = convertTemp(
                        data.daily.temperature_2m_max[index],
                        unit
                    )

                    val minT = convertTemp(
                        data.daily.temperature_2m_min[index],
                        unit
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(15.dp)
                        ) {
                            Text("Date: $day")
                            Text("Max: ${maxT.roundToInt()}°$unit")
                            Text("Min: ${minT.roundToInt()}°$unit")
                        }
                    }
                }
            }
        }
    }
}

// ✅ Celsius → Fahrenheit
fun convertTemp(celsius: Double, unit: String): Double {
    return if (unit == "F") {
        (celsius * 9 / 5) + 32
    } else celsius
}
