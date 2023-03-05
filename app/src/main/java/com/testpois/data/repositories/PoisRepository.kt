package com.testpois.data.repositories

import com.testpois.domain.extensions.DomainError
import com.testpois.domain.extensions.Either
import com.testpois.features.getPois.domain.model.PoisData

interface PoisRepository {
    suspend fun getPois(): Either<PoisData, DomainError>
}
