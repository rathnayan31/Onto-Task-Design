package com.example.taskonto.data.repository

import com.example.taskonto.data.api.ApiHelper
import com.example.taskonto.data.model.Bookings
import com.example.taskonto.data.model.EVData
import com.example.taskonto.remote.NetworkHelper
import com.example.taskonto.utils.ApiResult
import javax.inject.Inject

open class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {
     open suspend fun getEVDataDetails() : ApiResult<EVData> =
         NetworkHelper.callApi { apiHelper.getEvDataDetails() }
}