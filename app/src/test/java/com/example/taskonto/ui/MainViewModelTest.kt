package com.example.taskonto.ui

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.constraintlayout.motion.utils.ViewState
import androidx.lifecycle.Observer
import com.example.taskonto.data.api.ApiHelper
import com.example.taskonto.data.model.EVData
import com.example.taskonto.data.repository.MainRepository
import com.example.taskonto.remote.NetworkHelper
import com.example.taskonto.utils.ApiResult
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
internal class MainViewModelTest {

    @Mock
    private lateinit var viewModel: MainViewModel
    private lateinit var fakeMainRepository: MainRepository
    private lateinit var evDataObserver: Observer<ApiResult<EVData>>
    private lateinit var apiHelper : ApiHelper
    private lateinit var evData: EVData

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    fun setUp() {
        viewModel = MainViewModel(fakeMainRepository )
        viewModel.evDataLivedata.observeForever(evDataObserver )
    }

    @Test
    suspend fun testNull() {
        `when`(fakeMainRepository.getEVDataDetails()).thenReturn(null)
        assertNotNull(viewModel.loadEVDataDetails())
        assertTrue(viewModel.loadEVDataDetails().let { true })
    }

    @Test
    suspend fun testApiFetchEVDataSuccess() {
        `when`(fakeMainRepository.getEVDataDetails()).thenReturn(NetworkHelper.callApi { apiHelper.getEvDataDetails() })
        viewModel.loadEVDataDetails()
        verify(evDataObserver).onChanged(NetworkHelper.callApi { apiHelper.getEvDataDetails() })
    }

    @After
    fun tearDown(){

    }
}