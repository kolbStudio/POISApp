package com.testpois.data.mappers

import com.testpois.data.remote.response.PoisDTO
import com.testpois.data.remote.response.PoisDataDTO
import com.testpois.features.getPois.domain.model.Pois
import com.testpois.features.getPois.domain.model.PoisData

fun PoisDataDTO.toDomain() = PoisData(list.map { it.toDomain() })

fun PoisDTO.toDomain() = Pois(id, title, geoCoordinates, image)
