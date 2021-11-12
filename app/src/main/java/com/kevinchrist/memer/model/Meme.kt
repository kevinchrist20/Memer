package com.kevinchrist.memer.model

import android.os.Parcelable
import com.billkainkoom.ogya.quicklist.Listable
import com.billkainkoom.ogya.quicklist.ListableType
import com.kevinchrist.memer.utils.ListableTypes
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Meme(
    @Json(name = "box_count") val boxCount: Int,
    @Json(name = "height") val height: Int,
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String,
    @Json(name = "width") val width: Int
) : Parcelable, Listable() {
    override fun getListableType(): ListableType? {
        return ListableTypes.meme
    }
}