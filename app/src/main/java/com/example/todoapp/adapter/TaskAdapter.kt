package com.example.todoapp.adapter

import com.example.todoapp.model.Task
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.modules.EditTaskScreen
import com.example.todoapp.viewModel.TaskViewModel

class TaskAdapter(private var tasks: List<Task>, private val context: Context?) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
   // private lateinit var taskViewModel: TaskViewModel
    private val taskViewModel: TaskViewModel by lazy {
        ViewModelProvider((context as? AppCompatActivity)?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_activity_main) ?: error("Activity not found")).get(TaskViewModel::class.java)
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.task_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.task_description)
        val dueDateView: TextView = itemView.findViewById(R.id.dateview)
        val dueTimeView: TextView = itemView.findViewById(R.id.timeView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = tasks[position]
                //        tasks.drop(position)
//                    taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

                    taskViewModel.deleteTask(task)
                    taskViewModel.getAllTasks { tasks ->
                        updateTasks(tasks)
                    }
               //     Log.d("ser",task.toString());
              //     updateTasks(tasks)

                //    val action = CurrentFragmentDirections.actionToEditTaskFragment(task.id)
                //    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = tasks[position]
        holder.titleTextView.text = currentTask.taskTitle
        holder.descriptionTextView.text = currentTask.taskDescription
        holder.dueDateView.text = currentTask.dueDate
        holder.dueTimeView.text = currentTask.dueTime
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
