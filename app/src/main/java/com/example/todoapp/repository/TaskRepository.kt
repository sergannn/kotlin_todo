package com.example.todoapp.repository

import com.example.todoapp.database.TaskDao
import com.example.todoapp.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks()
    }

    suspend fun getTaskById(taskId: Int): Task? {
        return taskDao.getTaskById(taskId)
    }
}
