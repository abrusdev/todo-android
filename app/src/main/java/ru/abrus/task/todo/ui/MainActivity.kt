package ru.abrus.task.todo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.abrus.task.todo.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: BaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.tasks.observe(this) {
            Log.i("RRR", "onCreate: ${it.size}")
        }

    }
}