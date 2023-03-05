package com.testpois.data.extensions

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T): ApiResponse<T>()
    data class BadRequest(val error: String?): ApiResponse<Nothing>()
    data class ServerError(val error: String?): ApiResponse<Nothing>()
    data class ApiError(val error: String?): ApiResponse<Nothing>()
    data class Unauthorized(val errorBody: String?) : ApiResponse<Nothing>()
    object NoDataResponse : ApiResponse<Nothing>()
    object Timeout : ApiResponse<Nothing>()
}
