package com.bambang.githubapi.di

import android.content.Context
import androidx.room.Room
import com.bambang.githubapi.data.local.database.AppDatabase
import com.bambang.githubapi.data.local.dao.UserDao
import com.bambang.githubapi.data.remote.api.GitHubApiService
import com.bambang.githubapi.data.repository.UserRepositoryImpl
import com.bambang.githubapi.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "github_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun provideUserRepository(
        api: GitHubApiService,
        dao: UserDao
    ): UserRepository {
        return UserRepositoryImpl(api, dao)
    }
}