package com.kevinchrist.memer.retrofit

import com.kevinchrist.memer.model.MemesResponse
import retrofit2.Response
import retrofit2.http.GET

interface MemeService {
    @GET("get_memes")
    suspend fun getMemes(): Response<MemesResponse>
}