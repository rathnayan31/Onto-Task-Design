package com.example.taskonto.data.api

import com.example.taskonto.data.model.EVData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("user.json")
    suspend fun getEVData(): Response<EVData>

}