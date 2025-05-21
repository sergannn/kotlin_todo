package com.example.todoapp.modules

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
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
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // Initialize with current date/time
        updateDueDateTextView()
        updateDueTimeTextView()

        binding.doneBtn.setOnClickListener {
            saveTask()
        }

        binding.textViewDueDate.setOnClickListener { showDatePickerDialog() }
        binding.textViewDueTime.setOnClickListener { showTimePickerDialog() }
    }

    private fun showDatePickerDialog() {
        DatePickerDialog(
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
        ).show()
    }

    private fun updateDueDateTextView() {
        binding.textViewDueDate.setText(dateFormat.format(calendar.time))
    }

    private fun showTimePickerDialog() {
        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                updateDueTimeTextView()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun updateDueTimeTextView() {
        binding.textViewDueTime.setText(timeFormat.format(calendar.time))
    }

    private fun saveTask() {
        val taskTitle = binding.taskTitle.text.toString().trim()
        val taskDescription = binding.taskDescription.text.toString().trim()
        val dueDate = binding.textViewDueDate.text.toString()
        val dueTime = binding.textViewDueTime.text.toString()

        if (taskTitle.isEmpty()) {
            Toast.makeText(this, "Please enter task title", Toast.LENGTH_SHORT).show()
            return
        }

        val task = Task(
            taskTitle = taskTitle,
            taskDescription = taskDescription,
            dueDate = dueDate,
            dueTime = dueTime
        )

        taskViewModel.insertTask(task)
        Toast.makeText(this, "Task Created Successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
}