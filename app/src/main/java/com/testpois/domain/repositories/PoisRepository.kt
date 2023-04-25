package com.testpois.domain.repositories

import com.testpois.domain.extensions.DomainError
import com.testpois.domain.extensions.Either
import com.testpois.features.getPois.domain.model.Pois

interface PoisRepository {
    suspend fun getPois(forceRefresh: Boolean): Either<List<Pois>, DomainError>
    suspend fun deletePois(pois: Pois)
}
