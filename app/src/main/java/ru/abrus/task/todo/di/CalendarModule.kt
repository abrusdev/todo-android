package ru.abrus.task.todo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.abrus.task.todo.storage.AppDatabase
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class CalendarModule {

    @Provides
    fun provideCalendar(): Calendar {
        return Calendar.getInstance()
    }

}