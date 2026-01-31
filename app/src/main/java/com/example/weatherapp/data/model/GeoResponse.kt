package com.example.weatherapp.data.model

data class GeoResponse(
    val results: List<GeoCity>?
)

data class GeoCity(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String
)
