package ru.abrus.task.todo.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAll(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE uid = :id")
    fun getTaskById(id: Int): TaskEntity

    @Insert
    fun insertTask(task: TaskEntity)

    @Insert
    fun insertTasks(vararg tasks: TaskEntity)

    @Query("UPDATE task SET name = :name, date = :date, time = :time, alarm = :alarm WHERE uid = :uid")
    fun updateTask(uid: Int, name: String, date: String, time: String, alarm: Int)

    @Delete
    fun delete(user: TaskEntity)
}