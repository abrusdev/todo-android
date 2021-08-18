package ru.abrus.task.todo.ui.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.screen_home.*
import ru.abrus.task.todo.ui.AddTaskActivity
import ru.abrus.task.todo.R

@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.screen_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClicks()

    }

    private fun initClicks() {
        addTask.setOnClickListener {
            startActivity(Intent(requireActivity(), AddTaskActivity::class.java))
        }
    }
}