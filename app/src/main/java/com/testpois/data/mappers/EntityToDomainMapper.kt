package com.testpois.data.mappers

import com.testpois.data.local.database.entity.PoisEntity
import com.testpois.features.getPois.domain.model.Pois


fun PoisEntity.toDomain() = Pois(id, title, geocoordinates, image)

fun Pois.toEntity() = PoisEntity(id, title,geoCoordinates, image)
