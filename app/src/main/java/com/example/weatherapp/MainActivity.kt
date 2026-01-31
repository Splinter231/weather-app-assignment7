package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.weatherapp.ui.screens.*
import com.example.weatherapp.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            val vm: WeatherViewModel = viewModel()

            var unit by remember { mutableStateOf("C") }

            NavHost(navController, startDestination = "search") {

                // Search Screen
                composable("search") {
                    SearchScreen(
                        vm = vm,
                        onNavigateToWeather = {
                            navController.navigate("weather")
                        },
                        onOpenSettings = {
                            navController.navigate("settings")
                        }
                    )
                }

                // Weather Screen
                composable("weather") {
                    WeatherScreen(
                        vm = vm,
                        unit = unit,
                        onBack = {
                            navController.popBackStack("search", inclusive = false)
                        },
                        onOpenSettings = {
                            navController.navigate("settings")
                        }
                    )
                }

                // Settings Screen
                composable("settings") {
                    SettingsScreen(
                        currentUnit = unit,
                        onUnitChange = { unit = it },
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
