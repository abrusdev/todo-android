package ru.abrus.task.todo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.abrus.task.todo.storage.AppDatabase
import ru.abrus.task.todo.storage.TaskDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getInstance(appContext)
    }

    @Singleton
    @Provides
    fun provideDao(database: AppDatabase): TaskDao {
        return database.taskDao()
    }

}