package com.testpois.domain.usecases

import com.testpois.domain.repositories.PoisRepository
import com.testpois.domain.extensions.DispatcherProvider
import com.testpois.domain.extensions.DataError
import com.testpois.domain.extensions.Either
import com.testpois.domain.extensions.FlowUseCase
import com.testpois.features.getPois.domain.model.Pois
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllPoisUseCase @Inject constructor(
    private val repository: PoisRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Boolean, Either<List<Pois>, DataError>>(dispatcherProvider) {

    override fun start(param: Boolean): Flow<Either<List<Pois>, DataError>> = flow {
        emit(repository.getPois(param))
    }
}
