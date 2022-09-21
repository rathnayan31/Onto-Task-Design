package com.example.taskonto.data.api

import com.example.taskonto.data.model.EVData
import retrofit2.Response

interface ApiHelper {
    suspend fun getEvDataDetails():Response<EVData>
}