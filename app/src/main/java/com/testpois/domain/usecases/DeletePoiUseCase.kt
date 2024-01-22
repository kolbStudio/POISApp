package com.testpois.domain.usecases

import com.testpois.domain.repositories.PoisRepository
import com.testpois.features.getPois.domain.model.Pois
import javax.inject.Inject

class DeletePoiUseCase @Inject constructor(private val poisRepository: PoisRepository){
    suspend fun invoke(param: Pois) {
        poisRepository.deletePois(param)
    }
}