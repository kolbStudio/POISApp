package com.testpois.data.dataSource

import com.testpois.data.remote.service.PoisService
import com.testpois.data.extensions.mapToApiResponse
import com.testpois.data.remote.response.PoisDataDTO
import com.testpois.data.extensions.toData
import com.testpois.domain.extensions.DataError
import com.testpois.domain.extensions.Either
import com.testpois.domain.extensions.eitherFailure
import com.testpois.domain.extensions.eitherSuccess
import java.net.UnknownHostException
import javax.inject.Inject

class PoisRemoteDataSource @Inject constructor(private val poisService: PoisService) {

    suspend fun getPois(): Either<PoisDataDTO, DataError> {
        return try {
            val response = poisService.getPois()
            when (val res = response.mapToApiResponse().toData()) {
                is Either.Failure -> res
                is Either.Success -> eitherSuccess(res.data)
            }
        } catch (ex: Exception) {
            return when (ex) {
                is UnknownHostException -> eitherFailure(DataError.TimeoutError)
                else -> eitherFailure(DataError.GenericExceptionError(ex.message))
            }
        }
    }

}
