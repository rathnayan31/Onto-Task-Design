package com.example.taskonto.remote

import com.example.taskonto.utils.ApiResult
import com.example.taskonto.utils.Loading
import com.example.taskonto.utils.Success
import com.example.taskonto.utils.Error
import com.example.taskonto.utils.Exception
import retrofit2.HttpException
import retrofit2.Response

class NetworkHelper {

    companion object {
        suspend fun <T : Any> callApi(
            execute: suspend () -> Response<T>,
        ): ApiResult<T> = try {
            Loading(null)
            val response = execute.invoke()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Success(body)
            } else {
                Error(code = response.code(), message = response.message())
            }
        } catch (e: HttpException) {
            Error(code = e.code(), message = e.message())
        } catch (e:Throwable){
            if (e is NoConnectivityException){
                Exception(Throwable(e.getError()))
            }else{
                Exception(e)
            }
        }
    }
}