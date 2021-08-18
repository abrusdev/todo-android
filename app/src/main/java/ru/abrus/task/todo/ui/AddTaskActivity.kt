package ru.abrus.task.todo.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_task.*
import ru.abrus.task.todo.R
import ru.abrus.task.todo.storage.TaskEntity
import ru.abrus.task.todo.utils.Constants
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddTaskActivity() : AppCompatActivity() {

    val viewModel: BaseViewModel by viewModels()

    @Inject
    lateinit var calendar: Calendar

    private var task = TaskEntity(0, "", "", "", Constants.BEFORE_15_MIN)

    private val dateWatcher by lazy {
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val result = "$dayOfMonth.${with(month) { if (this < 10) "0$this" else this }}.$year"
            task.date = result
            dateEdt.text = result
        }
    }

    private val timeWatcher by lazy {
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val result = "$hourOfDay:$minute"
            task.time = result
            timeEdt.text = result
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        initDefaultTime()

        initListeners()

        initClicks()
    }

    private fun initClicks() {
        createTask.setOnClickListener {
            if (task.name.isEmpty()) {
                nameEdt.error = "Please enter the task title"
                return@setOnClickListener
            }

            if (task.date.isEmpty()) {
                dateEdt.error = "Please enter the task date"
                return@setOnClickListener
            }

            if (task.time.isEmpty()) {
                timeEdt.error = "Please enter the task time"
                return@setOnClickListener
            }

            viewModel.insertTask(task)
            finish()
        }

        back.setOnClickListener {
            finish()
        }
    }

    private fun initListeners() {
        dateEdt.setOnClickListener {

            with(DatePickerDialog(this,
                dateWatcher,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )) {
                datePicker.minDate = System.currentTimeMillis() - 1000
                show()
            }
        }

        timeEdt.setOnClickListener {
            TimePickerDialog(this,
                timeWatcher,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        nameEdt.addTextChangedListener {
            task.name = it.toString()
        }
    }

    private fun initDefaultTime() {
        dateEdt.hint = String.format(
            "%s.%s.%s",
            calendar.get(Calendar.DAY_OF_MONTH),
            with(calendar.get(Calendar.MONTH)) { if (this < 10) "0$this" else this },
            calendar.get(Calendar.YEAR),
        )

        timeEdt.hint = String.format("%s:%s",
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE))
    }
}