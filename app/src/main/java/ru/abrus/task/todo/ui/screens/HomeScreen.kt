package ru.abrus.task.todo.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.screen_home.*
import ru.abrus.task.todo.R
import ru.abrus.task.todo.storage.TaskEntity
import ru.abrus.task.todo.ui.AddTaskActivity
import ru.abrus.task.todo.ui.BaseViewModel
import ru.abrus.task.todo.ui.adapters.TaskAdapter
import ru.abrus.task.todo.utils.getWeekName
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.screen_home) {

    val viewModel: BaseViewModel by viewModels()

    @Inject
    lateinit var calendar: Calendar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        initClicks()

        observe()
    }

    @SuppressLint("SimpleDateFormat")
    private fun initViews() {
        dateWeek.text = getWeekName(calendar.get(Calendar.DAY_OF_WEEK))

        val date = SimpleDateFormat("dd MMMM yyyy")
        todayFullDate.text = date.format(calendar.time)
    }

    private fun initClicks() {
        addTask.setOnClickListener {
            startActivity(Intent(requireActivity(), AddTaskActivity::class.java))
        }
    }

    private fun observe() {
        viewModel.tasks.observe(viewLifecycleOwner) {

            val activeTasks = arrayListOf<TaskEntity>()
            val tasks = arrayListOf<TaskEntity>()
            it.forEach { task ->
                if (task.isActive) activeTasks.add(task)
                else tasks.add(task)
            }

            taskMonitor.text =
                String.format(getString(R.string.s_task_today), "${activeTasks.size}/${it.size}")

            recyclerActiveTasks.adapter = TaskAdapter { id, isChecked ->
                viewModel.updateTask(id, isChecked)
            }.apply {
                setData(activeTasks)
            }

            recyclerTasks.adapter = TaskAdapter { id, isChecked ->
                viewModel.updateTask(id, isChecked)
            }.apply {
                setData(tasks)
            }
        }
    }
}