package com.example.todoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val taskTitle: String,
    val taskDescription: String,
    var dueDate: String,
    var dueTime: String
)
