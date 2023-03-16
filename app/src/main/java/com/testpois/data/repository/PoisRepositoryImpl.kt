package com.testpois.data.repository

import com.testpois.data.dataSource.PoisLocalDataSource
import com.testpois.data.dataSource.PoisRemoteDataSource
import com.testpois.data.mappers.toDomain
import com.testpois.domain.extensions.DomainError
import com.testpois.domain.extensions.Either
import com.testpois.domain.extensions.eitherFailure
import com.testpois.domain.extensions.eitherSuccess
import com.testpois.domain.extensions.getOrNull
import com.testpois.features.getPois.domain.model.Pois
import com.testpois.features.getPois.repositories.PoisRepository
import javax.inject.Inject

class PoisRepositoryImpl @Inject constructor(
    private val poisRemoteDataSource: PoisRemoteDataSource,
    private val poisLocalDataSource: PoisLocalDataSource
) : PoisRepository {

    override suspend fun getPois(forceRefresh: Boolean): Either<List<Pois>, DomainError> {
        if (forceRefresh) deleteLocalPois()
        val localPois = poisLocalDataSource.getPoisFromDb()
        return if (localPois.isNotEmpty()) eitherSuccess(localPois.map { it.toDomain() })
        else eitherSuccess(getRemotePois())
    }

    override suspend fun deletePois(pois: Pois) {
        poisLocalDataSource.deletePoi(pois)
    }

    private suspend fun getRemotePois(): List<Pois> {
        val remotePois = poisRemoteDataSource.getPois().getOrNull()
        if (!remotePois?.list.isNullOrEmpty()) {
            val allPois = remotePois?.list?.map { it.toDomain() } ?: emptyList()
            allPois.forEach { pois ->
                poisLocalDataSource.insertPoi(pois)
            }
            return allPois
        }
        return emptyList()
    }

    private suspend fun deleteLocalPois() {
        poisLocalDataSource.getPoisFromDb().forEach {
            poisLocalDataSource.deletePoi(it.toDomain())
        }
    }
}
