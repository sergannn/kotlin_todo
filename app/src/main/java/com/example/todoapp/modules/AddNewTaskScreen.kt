package com.example.todoapp.modules

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.databinding.ActivityAddNoteBinding
import com.example.todoapp.model.Task
import com.example.todoapp.viewModel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddNewTaskScreen : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var taskViewModel: TaskViewModel
    private var taskDescription: String = ""
    private var taskTitle: String = ""
    private var dueDate: String = ""
    private var dueTime: String = ""
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        binding.doneBtn.setOnClickListener {
            taskTitle = binding.taskTitle.text.toString()
            taskDescription = binding.taskDescription.text.toString()
            dueDate = binding.textViewDueDate.text.toString()
            dueTime = binding.textViewDueTime.text.toString()
            saveTask()
        }

        binding.textViewDueDate.setOnClickListener { showDatePickerDialog() }
        binding.textViewDueTime.setOnClickListener { showTimePickerDialog() }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDueDateTextView()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun updateDueDateTextView() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.textViewDueDate.text = dateFormat.format(calendar.time)
    }

    private fun showTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                updateDueTimeTextView()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }

    private fun updateDueTimeTextView() {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        binding.textViewDueTime.text = timeFormat.format(calendar.time)
    }

    private fun saveTask() {
        val task = Task(
            taskTitle = taskTitle,
            taskDescription = taskDescription,
            dueDate = dueDate,
            dueTime = dueTime
        )

        taskViewModel.insertTask(task)
        Toast.makeText(this, "Task Created Successfully", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, HomeScreen::class.java))
        finish()
    }
}
