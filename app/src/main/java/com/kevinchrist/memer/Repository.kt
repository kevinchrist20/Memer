package com.kevinchrist.memer

import com.kevinchrist.memer.retrofit.MemeApiClient

class Repository {
    private val apiClient = MemeApiClient.getAPIService()

    suspend fun getMemes() = apiClient.getMemes()
}