package com.example.taskonto.remote

import okio.IOException

class NoConnectivityException : IOException() {
    fun getError(): String = "No Internet Connection"
}
