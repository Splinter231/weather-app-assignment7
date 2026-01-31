package com.example.weatherapp.data.repository

import android.content.Context
import com.example.weatherapp.data.api.GeoApi
import com.example.weatherapp.data.api.WeatherApi
import com.example.weatherapp.data.model.WeatherResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository(context: Context) {

    private val geoApi: GeoApi
    private val weatherApi: WeatherApi

    private val prefs =
        context.getSharedPreferences("weather_cache", Context.MODE_PRIVATE)

    private val gson = Gson()

    init {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        geoApi = Retrofit.Builder()
            .baseUrl("https://geocoding-api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(GeoApi::class.java)

        weatherApi = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(WeatherApi::class.java)
    }

    // ✅ теперь возвращаем Triple:
    // WeatherResponse + isOffline + cachedCityName
    suspend fun getWeather(
        city: String
    ): Result<Triple<WeatherResponse, Boolean, String>> {

        return try {

            // ✅ geo поиск
            val geo = geoApi.searchCity(city)

            val firstCity = geo.results?.firstOrNull()
                ?: return Result.failure(Exception("❌ City not found"))

            // ✅ запрос погоды
            val weather = weatherApi.getWeather(
                lat = firstCity.latitude,
                lon = firstCity.longitude
            )

            // ✅ сохраняем cache
            prefs.edit()
                .putString("last_weather", gson.toJson(weather))
                .putString("last_city", city)
                .apply()

            // ✅ online result
            Result.success(Triple(weather, false, city))

        } catch (e: Exception) {

            // ✅ offline fallback
            val cachedWeather = prefs.getString("last_weather", null)
            val cachedCity =
                prefs.getString("last_city", "Unknown City") ?: "Unknown City"


            if (cachedWeather != null) {

                val weather =
                    gson.fromJson(cachedWeather, WeatherResponse::class.java)

                // ✅ offline result
                Result.success(Triple(weather, true, cachedCity))

            } else {
                Result.failure(Exception("🌐 No internet and no cached data"))
            }
        }
    }
}
