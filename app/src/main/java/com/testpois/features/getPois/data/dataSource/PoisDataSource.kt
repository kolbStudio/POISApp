package com.testpois.features.getPois.data.dataSource

import com.testpois.features.getPois.data.service.PoisService
import com.testpois.data.extensions.mapToApiResponse
import com.testpois.features.getPois.data.model.response.PoisDataDTO
import com.testpois.data.extensions.toDomain
import com.testpois.domain.extensions.DomainError
import com.testpois.domain.extensions.Either
import com.testpois.domain.extensions.eitherFailure
import com.testpois.domain.extensions.eitherSuccess
import java.net.UnknownHostException
import javax.inject.Inject

class PoisDataSource @Inject constructor(private val poisService: PoisService) {

    suspend fun getPois(): Either<PoisDataDTO, DomainError> {
        return try {
            val response = poisService.getPois()
            when (val res = response.mapToApiResponse().toDomain()){
                is Either.Failure -> res
                is Either.Success -> eitherSuccess(res.data)
            }
        } catch (ex: Exception) {
            return when (ex) {
                is UnknownHostException -> eitherFailure(DomainError.TimeoutError)
                else -> eitherFailure(DomainError.GenericExceptionError(ex.message))
            }
        }
    }

}
