package com.testpois.features.getPois.data.repository

import com.testpois.data.repositories.PoisRepository
import com.testpois.features.getPois.data.mappers.toDomain
import com.testpois.features.getPois.data.dataSource.PoisDataSource
import com.testpois.domain.extensions.DomainError
import com.testpois.domain.extensions.Either
import com.testpois.domain.extensions.eitherSuccess
import com.testpois.features.getPois.domain.model.PoisData
import javax.inject.Inject

class PoisRepositoryImpl @Inject constructor(
    private val poisDataSource: PoisDataSource
    ) : PoisRepository {

    override suspend fun getPois(): Either<PoisData, DomainError> {
        return when (val res = poisDataSource.getPois()) {
            is Either.Failure -> res
            is Either.Success -> eitherSuccess(res.data.toDomain())
        }
    }
}
