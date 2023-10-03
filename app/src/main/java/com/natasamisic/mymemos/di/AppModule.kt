package com.natasamisic.mymemos.di

import android.app.Application
import androidx.room.Room
import com.natasamisic.mymemos.feature.data.data_source.db.MemoDatabase
import com.natasamisic.mymemos.feature.data.data_source.MemoLocalDataSource
import com.natasamisic.mymemos.feature.data.repository.MemoRepositoryImpl
import com.natasamisic.mymemos.feature.domain.repository.MemoRepository
import com.natasamisic.mymemos.feature.domain.use_case.AddMemoUseCase
import com.natasamisic.mymemos.feature.domain.use_case.DeleteMemoUseCase
import com.natasamisic.mymemos.feature.domain.use_case.GetMemoUseCase
import com.natasamisic.mymemos.feature.domain.use_case.GetMemosUseCase


import com.natasamisic.mymemos.feature.domain.use_case.MemoUseCases

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMemoDatabase(app: Application): MemoDatabase {
        return Room.databaseBuilder(
            app,
            MemoDatabase::class.java,
            MemoDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMemoDataSource(db: MemoDatabase): MemoLocalDataSource {
        return MemoLocalDataSource(db.memosDao)
    }
    @Provides
    @Singleton
    fun provideMemoRepository(ds: MemoLocalDataSource): MemoRepository {
        return MemoRepositoryImpl(ds)
    }

    @Provides
    @Singleton
    fun provideMemoUseCases(repository: MemoRepository): MemoUseCases {
        return MemoUseCases(
            getMemosUseCase = GetMemosUseCase(repository),
            deleteMemoUseCase = DeleteMemoUseCase(repository),
            addMemoUseCase = AddMemoUseCase(repository),
            getMemoUseCase = GetMemoUseCase(repository)
        )
    }


}