package com.example.taskonto.data.repository

import androidx.work.ListenableWorker
import com.example.taskonto.data.api.ApiHelper
import com.example.taskonto.data.model.EVData
import com.example.taskonto.remote.NetworkHelper
import com.example.taskonto.utils.ApiResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.`when`
import java.util.*


internal class FakeMainRepositoryTest(apiHelper: ApiHelper) : MainRepository(apiHelper) {
    private var shouldReturnNetworkError = false
    lateinit var apiHelper: ApiHelper
    lateinit var mainRepository: MainRepository
    lateinit var evData: EVData

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }


    @Test
    fun data_evData_returnBookingDetailsStored() {
        runBlocking {
            `when`(mainRepository.getEVDataDetails()).thenReturn(NetworkHelper.callApi { apiHelper.getEvDataDetails() })
            val result = evData.bookingDetails
            Assert.assertEquals(result, ListenableWorker.Result.success())
        }
    }

    @Test
    fun data_evData_returnlastEnergyLevelStored() {
        runBlocking {
            `when`(mainRepository.getEVDataDetails()).thenReturn(NetworkHelper.callApi { apiHelper.getEvDataDetails() })
            val result = evData.bookingDetails?.get(0)?.car?.lastEnergyLevel
            Assert.assertEquals(result, ListenableWorker.Result.success())
        }
    }

    @Test
    fun data_evData_returnsubscriptionMilesLeftStored() {
        runBlocking {
            `when`(mainRepository.getEVDataDetails()).thenReturn(NetworkHelper.callApi { apiHelper.getEvDataDetails() })
            val result = evData.bookingDetails?.get(0)?.subscriptionMilesLeft
            Assert.assertEquals(result, ListenableWorker.Result.success())
        }
    }
}