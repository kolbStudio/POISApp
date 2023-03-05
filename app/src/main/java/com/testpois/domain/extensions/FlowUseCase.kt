package com.testpois.domain.extensions

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<T, R>(protected open val dispatcherProvider: DispatcherProvider) {
    protected open fun dispatcher() : CoroutineDispatcher = dispatcherProvider.io()
    fun execute(param: T) = start(param).flowOn(dispatcher())

    protected abstract fun start(param: T): Flow<R>
}
