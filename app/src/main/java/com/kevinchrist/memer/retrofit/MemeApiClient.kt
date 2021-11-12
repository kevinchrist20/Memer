package com.kevinchrist.memer.retrofit

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object MemeApiClient {
    private var instance: Retrofit? = null
    private const val BASE_URL = "https://api.imgflip.com/"

    private val mInstance: Retrofit
        get() {
            if (instance == null)
                instance = Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
            return instance!!
        }

    fun getAPIService(): MemeService {
        return mInstance.create(MemeService::class.java)
    }
}