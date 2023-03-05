package com.testpois.features.getPois.data.mappers

import com.testpois.features.getPois.data.model.response.PoisDTO
import com.testpois.features.getPois.data.model.response.PoisDataDTO
import com.testpois.features.getPois.domain.model.Pois
import com.testpois.features.getPois.domain.model.PoisData

fun PoisDataDTO.toDomain() = PoisData(
    list.map {
        it.toDomain()
    }
)

fun PoisDTO.toDomain() = Pois(id, title, geoCoordinates, image)
