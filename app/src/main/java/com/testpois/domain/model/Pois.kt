package com.testpois.features.getPois.domain.model

data class PoisData(
    val list: List<Pois>
)

data class Pois(
    val id: Int,
    val title: String,
    val geoCoordinates: String,
    val image: String,
)
