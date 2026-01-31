package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    currentUnit: String,
    onUnitChange: (String) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        // Верхняя панель
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { onBack() }) {
                Text("⬅ Back")
            }

            Text("⚙ Settings", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text("Temperature Units:", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(15.dp))

        Row {

            Button(
                onClick = { onUnitChange("C") },
                enabled = currentUnit != "C"
            ) {
                Text("Celsius °C")
            }

            Spacer(modifier = Modifier.width(15.dp))

            Button(
                onClick = { onUnitChange("F") },
                enabled = currentUnit != "F"
            ) {
                Text("Fahrenheit °F")
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text("Changes apply instantly", style = MaterialTheme.typography.bodyLarge)
    }
}
