package com.example.todoapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.TaskDatabase
import com.example.todoapp.model.Task
import com.example.todoapp.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository

    init {
        val taskDao = TaskDatabase.getInstance(application).getTaskDao()
        repository = TaskRepository(taskDao)
    }

    // Insert Task
    fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
        }
    }

    // Delete Task
    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }

    // Update Task
    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    // Get All Tasks and update UI on the main thread
    fun getAllTasks(onResult: (List<Task>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = repository.getAllTasks()
            // Switch to Main thread to update UI
            withContext(Dispatchers.Main) {
                onResult(tasks)
            }
        }
    }

    // Get Task by ID and update UI on the main thread
    fun getTaskById(taskId: Int, onResult: (Task?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val task = repository.getTaskById(taskId)
            // Switch to Main thread to update UI
            withContext(Dispatchers.Main) {
                onResult(task)
            }
        }
    }
}
