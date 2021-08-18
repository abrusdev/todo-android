package ru.abrus.task.todo.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_edit_task.*
import ru.abrus.task.todo.R
import ru.abrus.task.todo.storage.TaskEntity
import ru.abrus.task.todo.utils.Constants
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class EditTaskActivity : AppCompatActivity() {

    val viewModel: BaseViewModel by viewModels()

    var uid: Int = -1

    @Inject
    lateinit var calendar: Calendar

    private var task = TaskEntity(0, "", "", "", Constants.AT_TIME)

    private val dateWatcher by lazy {
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val result = "$dayOfMonth.${with(month) { if (this < 10) "0$this" else this }}.$year"
            task.date = result
            dateEdt.text = result
        }
    }

    private val timeWatcher by lazy {
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val result = "$hourOfDay:${with(minute) { if (this < 10) "0$this" else this }}"
            task.time = result
            timeEdt.text = result
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        initDefaultTime()

        initListeners()

        initClicks()

        initSpinner()

        observe()
    }

    private fun observe() {
        uid = intent.getIntExtra("uid", -1)
        if (uid == -1)
            return

        viewModel.getTask(uid) {
            task = it
            updateUI()
        }

    }

    private fun initSpinner() {
        val ad = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,
            Constants.alarmTexts as List<Any?>)

        ad.setDropDownViewResource(
            android.R.layout
                .simple_spinner_dropdown_item);

        alarmSpinner.adapter = ad
        alarmSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                task.alarmType = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun initClicks() {
        deleteTask.setOnClickListener {
            viewModel.deleteTask(task)
            finish()
        }

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

            if (uid == -1)
                viewModel.insertTask(task)
            else
                viewModel.updateTask(uid, task.name, task.date, task.time, task.alarmType)
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
            with(calendar.get(Calendar.MINUTE)) { if (this < 10) "0$this" else this }
        )
    }

    private fun updateUI() {
        nameEdt.setText(task.name)
        dateEdt.text = task.date
        timeEdt.text = task.time

        alarmSpinner.setSelection(task.alarmType)

        deleteTask.isVisible = true
        createTask.text = getString(R.string.update_task)
    }
}