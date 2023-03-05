package com.testpois.domain.extensions

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed class Either<out T, out E> {
    data class Success<out T>(val data: T) : Either<T, Nothing>()
    data class Failure<out E>(val error: E) : Either<Nothing, E>()
}
@OptIn(ExperimentalContracts::class)
inline fun <T, E> Either<T, E>.onSuccess(action: (T) -> Unit): Either<T, E> {
    contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
    if (this is Either.Success) action(data)
    return this
}
@OptIn(ExperimentalContracts::class)
inline fun <T, E> Either<T, E>.onFailure(action: (E) -> Unit): Either<T, E> {
    contract { callsInPlace(action, InvocationKind.AT_MOST_ONCE) }
    if (this is Either.Failure) action(error)
    return this
}
fun <T, E> Either<T, E>.getErrorOrNull() = if (this is Either.Failure) error else null
fun <T, E> Either<T, E>.getOrNull() = if (this is Either.Success) data else null
fun <T, E> Either<T, E>.getOrThrow() = if (this is Either.Success) data else error("Error getting data")
inline val Either<*, *>.isSuccess
    get() = this is Either.Success
inline val Either<*, *>.isFailure
    get() = this is Either.Failure
fun <T> eitherFailure(error: T) = Either.Failure(error)
fun <T> eitherSuccess(data: T) = Either.Success(data)
