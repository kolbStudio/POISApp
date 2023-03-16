package com.testpois.data.mappers

import com.testpois.data.local.database.entity.PoisEntity
import com.testpois.data.remote.response.PoisDTO

fun PoisDTO.toEntity() = PoisEntity(id, title, geoCoordinates, image)