package com.testpois.domain.extensions

sealed class DataError {
    object TimeoutError : DataError()
    object ServerError : DataError()
    object ConnectivityError : DataError()
    object NoDataError : DataError()
    object MissingLocalStorageError : DataError()
    object ValidationError : DataError()
    data class UnauthorizedError(val errorBody: String?) : DataError()
    data class ApiError(val message: String?) : DataError()
    data class GenericExceptionError(val message: String?) : DataError()
}
