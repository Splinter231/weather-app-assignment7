package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun SearchScreen(
    vm: WeatherViewModel,
    onNavigateToWeather: () -> Unit,
    onOpenSettings: () -> Unit
) {
    var city by remember { mutableStateOf("") }

    val error by vm.error.collectAsState()
    val loading by vm.loading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("🌦 Weather App", style = MaterialTheme.typography.headlineSmall)

            Button(onClick = { onOpenSettings() }) {
                Text("⚙")
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter city name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                vm.fetchWeather(city) {
                    onNavigateToWeather()
                }
            }
        ) {
            Text("Search Weather")
        }

        Spacer(modifier = Modifier.height(15.dp))

        if (loading) CircularProgressIndicator()

        if (error.isNotBlank()) {
            Text(error, color = MaterialTheme.colorScheme.error)
        }
    }
}
