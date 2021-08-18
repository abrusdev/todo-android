package ru.abrus.task.todo.ui

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.abrus.task.todo.storage.TaskDao
import ru.abrus.task.todo.storage.TaskEntity
import javax.inject.Inject


@HiltViewModel
class BaseViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dao: TaskDao,
): ViewModel() {

    val tasks: LiveData<List<TaskEntity>> by lazy {
        dao.getAll()
    }

    fun insertTask(taskEntity: TaskEntity) {
        AsyncTask.execute {
            dao.insertTask(taskEntity)
        }
    }

    fun updateTask(id: Int, isChecked: Boolean) {
        AsyncTask.execute {
            dao.updateTask(id,isChecked)
        }
    }

}