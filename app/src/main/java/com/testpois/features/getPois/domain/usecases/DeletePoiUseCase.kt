package com.testpois.features.getPois.domain.usecases

import com.testpois.features.getPois.repositories.PoisRepository
import com.testpois.domain.extensions.DispatcherProvider
import com.testpois.domain.extensions.DomainError
import com.testpois.domain.extensions.Either
import com.testpois.domain.extensions.FlowUseCase
import com.testpois.features.getPois.domain.model.Pois
import com.testpois.features.getPois.domain.model.PoisData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeletePoiUseCase @Inject constructor(private val poisRepository: PoisRepository){
    suspend fun invoke(param: Pois) {
        poisRepository.deletePois(param)
    }
}