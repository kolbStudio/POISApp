package com.testpois.data.dataSource

import com.testpois.data.local.database.dao.PoiDao
import com.testpois.data.local.database.entity.PoisEntity
import com.testpois.data.mappers.toEntity
import com.testpois.features.getPois.domain.model.Pois
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PoisLocalDataSource @Inject constructor(private val poiDao: PoiDao) {

    suspend fun getPoisFromDb(): List<PoisEntity> {
        return poiDao.getListPois()
    }

    suspend fun insertPoi(pois: Pois) {
        poiDao.insertPoi(pois.toEntity())
    }

    suspend fun deletePoi(pois: Pois) {
        poiDao.deletePoi(pois.toEntity())
    }

}
