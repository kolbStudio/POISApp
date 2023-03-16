package com.testpois.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.testpois.data.local.database.dao.PoiDao
import com.testpois.data.local.database.entity.PoisEntity

@Database(entities = [PoisEntity::class], version = 1)
abstract class PoiDataBase : RoomDatabase() {
    abstract fun poiDao(): PoiDao
}
