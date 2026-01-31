package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.GeoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoApi {

    @GET("v1/search")
    suspend fun searchCity(
        @Query("name") city: String
    ): GeoResponse
}
