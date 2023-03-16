package com.testpois.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.testpois.data.local.database.entity.PoisEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PoiDao {

    @Query("SELECT * FROM PoisEntity")
    fun getListPois(): List<PoisEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoi(item : PoisEntity)

    @Update
    suspend fun updatePoi(item : PoisEntity)

    @Delete
    suspend fun deletePoi(item : PoisEntity)
}
