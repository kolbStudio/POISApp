package com.testpois.features.getPois.data.model.response

import com.google.gson.annotations.SerializedName

data class PoisDataDTO(
    @SerializedName("list") val list: List<PoisDTO>
)

data class PoisDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("geocoordinates") val geoCoordinates: String,
    @SerializedName("image") val image: String,
)
