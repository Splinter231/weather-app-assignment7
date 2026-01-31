package com.example.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = WeatherRepository(app)

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather: StateFlow<WeatherResponse?> = _weather

    private val _cityName = MutableStateFlow("")
    val cityName: StateFlow<String> = _cityName

    private val _offline = MutableStateFlow(false)
    val offline: StateFlow<Boolean> = _offline

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchWeather(city: String, onSuccess: () -> Unit) {

        if (city.isBlank()) {
            _error.value = "⚠ Enter city name"
            return
        }

        viewModelScope.launch {

            _loading.value = true
            _error.value = ""

            val result = repository.getWeather(city)

            result.onSuccess { (data, isOffline, cachedCity) ->

                _weather.value = data

                // ✅ важно: если offline → показываем cached city
                _cityName.value =
                    if (isOffline) cachedCity else city

                _offline.value = isOffline

                onSuccess()
            }

            result.onFailure {
                _error.value = it.message ?: "Unknown error"
            }

            _loading.value = false
        }
    }
}
