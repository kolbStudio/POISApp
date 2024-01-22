package com.testpois.data.extensions

import com.testpois.domain.extensions.DataError
import com.testpois.domain.extensions.Either
import com.testpois.domain.extensions.eitherFailure
import com.testpois.domain.extensions.eitherSuccess
import retrofit2.Response

fun <T> Response<T>.mapToApiResponse() : ApiResponse<T> {
    return when(HttpCodes.fromCode(this.code())) {
        HttpCodes.OK -> {
            body()?.let {
                ApiResponse.Success(it)
            } ?: ApiResponse.NoDataResponse
        }
        HttpCodes.NOT_FOUND, HttpCodes.SERVER_ERROR,
        HttpCodes.BAD_REQUEST -> ApiResponse.BadRequest(this.errorBody()?.string())
        HttpCodes.FORBIDDEN,
        HttpCodes.UNAUTHORIZED -> ApiResponse.Unauthorized(this.errorBody()?.string())
        HttpCodes.TIME_OUT -> ApiResponse.Timeout
    }
}

fun <T> ApiResponse<T>.toData(): Either<T, DataError> {
    return when (this) {
        is ApiResponse.Success -> eitherSuccess(this.data)
        is ApiResponse.ServerError,
        is ApiResponse.BadRequest -> eitherFailure(DataError.ServerError)
        is ApiResponse.NoDataResponse -> eitherFailure(DataError.NoDataError)
        is ApiResponse.Timeout -> eitherFailure(DataError.TimeoutError)
        is ApiResponse.Unauthorized -> eitherFailure(DataError.UnauthorizedError(this.errorBody))
        is ApiResponse.ApiError -> eitherFailure(DataError.ApiError(this.error))
    }
}
