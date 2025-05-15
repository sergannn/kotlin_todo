package com.example.todoapp.modules

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityEditBinding
import com.example.todoapp.model.Task
import com.example.todoapp.viewModel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

class EditTaskScreen : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private lateinit var taskViewModel: TaskViewModel
    private var taskId: Int = -1
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskId = intent.getIntExtra("taskId", -1)
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        taskViewModel.getTaskById(taskId) { task ->
            task?.let {
                binding.taskTitle.text = Editable.Factory.getInstance().newEditable(it.taskTitle)
                binding.taskDescription.text = Editable.Factory.getInstance().newEditable(it.taskDescription)
                binding.textViewDueDate.text = it.dueDate
                binding.textViewDueTime.text = it.dueTime
            }
        }

        binding.doneBtn.setOnClickListener { saveEditedTask() }
        binding.deleteBtn.setOnClickListener { showConfirmationDialog() }

        binding.textViewDueDate.setOnClickListener { showDatePickerDialog() }
        binding.textViewDueTime.setOnClickListener { showTimePickerDialog() }
    }

    private fun saveEditedTask() {
        val editedTask = Task(
            id = taskId,
            taskTitle = binding.taskTitle.text.toString(),
            taskDescription = binding.taskDescription.text.toString(),
            dueDate = binding.textViewDueDate.text.toString(),
            dueTime = binding.textViewDueTime.text.toString()
        )

        taskViewModel.updateTask(editedTask)
        Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show()
      //  startActivity(Intent(this, HomeScreen::class.java))
        finish()
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

    private fun showConfirmationDialog() {
        val dialog = Dialog(this, R.style.CustomDialogTheme)
        dialog.setContentView(R.layout.delete_confromation_popup)
        dialog.findViewById<Button>(R.id.ok_btn).setOnClickListener {
            taskViewModel.deleteTask(Task(id = taskId, taskTitle = "", taskDescription = "", dueDate = "", dueTime = ""))
           // startActivity(Intent(this, HomeScreen::class.java))
            finish()
        }
        dialog.findViewById<Button>(R.id.cancel_btn).setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}
