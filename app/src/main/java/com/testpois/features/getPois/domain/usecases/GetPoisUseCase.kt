package com.testpois.features.getPois.domain.usecases

import com.testpois.features.getPois.data.repository.PoisRepositoryImpl
import com.testpois.domain.extensions.DispatcherProvider
import com.testpois.domain.extensions.DomainError
import com.testpois.domain.extensions.Either
import com.testpois.domain.extensions.FlowUseCase
import com.testpois.features.getPois.domain.model.PoisData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPoisUseCase @Inject constructor(
    private val repository: PoisRepositoryImpl,
    dispatcherProvider: DispatcherProvider
    ) : FlowUseCase<Unit, Either<PoisData, DomainError>>(dispatcherProvider) {

    override fun start(param: Unit): Flow<Either<PoisData, DomainError>> = flow {
        emit(repository.getPois())
    }
}
