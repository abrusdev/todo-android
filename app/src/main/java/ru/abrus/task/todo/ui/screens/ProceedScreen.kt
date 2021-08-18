package ru.abrus.task.todo.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.screen_proceed.*
import ru.abrus.task.todo.R
import ru.abrus.task.todo.storage.TaskEntity
import ru.abrus.task.todo.ui.EditTaskActivity
import ru.abrus.task.todo.ui.BaseViewModel
import ru.abrus.task.todo.ui.adapters.TaskAdapter
import ru.abrus.task.todo.utils.getWeekName
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ProceedScreen : Fragment(R.layout.screen_proceed) {

    val viewModel: BaseViewModel by viewModels()

    @Inject
    lateinit var calendar: Calendar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
    }

    @SuppressLint("SimpleDateFormat")
    private fun observe() {
        viewModel.tasks.observe(viewLifecycleOwner) {

            val tasks = arrayListOf<TaskEntity>()

            it.forEach {
                val dateChars = it.date.split(".").map { Integer.parseInt(it) }
                val timeChar = it.time.split(":").map { Integer.parseInt(it) }

                val taskDate = Calendar.getInstance()
                taskDate.set(dateChars[2], dateChars[1], dateChars[0], timeChar[1], timeChar[0])
                if (taskDate.after(calendar)){
                    tasks.add(it)
                }
            }
            Log.i("RRR", "observe: ${tasks.size}")

            recyclerTasks.adapter = TaskAdapter({
                with(Intent(requireActivity(), EditTaskActivity::class.java)){
                    putExtra("uid", it)
                    startActivity(this)
                }
            }, { id, isChecked ->
                viewModel.updateTask(id, isChecked)
            }).apply {
                setData(tasks)
            }
        }
    }
}