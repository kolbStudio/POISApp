package com.testpois.domain.extensions

sealed class DomainError {
    object TimeoutError : DomainError()
    object ServerError : DomainError()
    object ConnectivityError : DomainError()
    object NoDataError : DomainError()
    object MissingLocalStorageError : DomainError()
    object ValidationError : DomainError()
    data class UnauthorizedError(val errorBody: String?) : DomainError()
    data class ApiError(val message: String?) : DomainError()
    data class GenericExceptionError(val message: String?) : DomainError()
}
