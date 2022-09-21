package com.example.taskonto.data.api

import com.example.taskonto.data.model.EVData
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getEvDataDetails(): Response<EVData> {
        return apiService.getEVData()
    }
}