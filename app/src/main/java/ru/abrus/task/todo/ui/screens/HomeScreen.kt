package ru.abrus.task.todo.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.abrus.task.todo.R

@AndroidEntryPoint
class HomeScreen: Fragment(R.layout.screen_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}