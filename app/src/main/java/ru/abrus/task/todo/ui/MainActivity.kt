package ru.abrus.task.todo.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import ru.abrus.task.todo.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: BaseViewModel by viewModels()

    fun homeNavigation() = Navigation.findNavController(this, R.id.fragmentContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.menu_home -> homeNavigation().navigate(R.id.homeScreen)
                R.id.menu_proceed -> homeNavigation().navigate(R.id.proceedScreen)
                R.id.menu_preceding -> homeNavigation().navigate(R.id.precedingScreen)
            }
            return@setOnItemSelectedListener true
        }
    }
}