package com.testpois.features.getPois.domain.usecases

import com.testpois.features.getPois.domain.repositories.PoisRepository
import com.testpois.domain.extensions.DispatcherProvider
import com.testpois.domain.extensions.DomainError
import com.testpois.domain.extensions.Either
import com.testpois.domain.extensions.FlowUseCase
import com.testpois.features.getPois.domain.model.Pois
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllPoisUseCase @Inject constructor(
    private val repository: PoisRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Boolean, Either<List<Pois>, DomainError>>(dispatcherProvider) {

    override fun start(param: Boolean): Flow<Either<List<Pois>, DomainError>> = flow {
        emit(repository.getPois(param))
    }
}
