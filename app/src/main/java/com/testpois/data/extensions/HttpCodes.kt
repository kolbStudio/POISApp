package com.testpois.data.extensions

enum class HttpCodes (val code: Int) {
    OK(200),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    SERVER_ERROR(500),
    TIME_OUT(503);

    companion object {
        fun fromCode(code: Int) = values().firstOrNull { c -> c.code == code } ?: SERVER_ERROR
    }
}
