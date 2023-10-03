package com.natasamisic.mymemos.di

import android.app.Application
import androidx.room.Room
import com.natasamisic.mymemos.feature.data.data_source.MemoDatabase
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
    fun provideMemoRepository(db: MemoDatabase): MemoRepository {
        return MemoRepositoryImpl(db.MemosDao)
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