package com.kevinchrist.memer.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MemesResponse(
    @Json(name = "data") val results: Data,
    @Json(name = "success") val success: Boolean
)