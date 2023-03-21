package com.testpois.core.di

import android.content.Context
import androidx.room.Room
import com.testpois.core.common.DATABASE_NAME
import com.testpois.data.local.database.dao.PoiDao
import com.testpois.data.local.database.PoiDataBase
import com.testpois.features.getPois.domain.repositories.PoisRepository
import com.testpois.data.dataSource.PoisLocalDataSource
import com.testpois.data.dataSource.PoisRemoteDataSource
import com.testpois.data.repository.PoisRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesPoisDatabase(@ApplicationContext appContext: Context): PoiDataBase {
        return Room
            .databaseBuilder(appContext, PoiDataBase::class.java, DATABASE_NAME)
            .build()
    }

    @Provides
    fun providesPoisDao(db: PoiDataBase): PoiDao {
        return db.poiDao()
    }

    @Provides
    internal fun providePoisDatabaseRepository(ds: PoisRemoteDataSource, db : PoisLocalDataSource):
            PoisRepository = PoisRepositoryImpl(ds, db)

}
