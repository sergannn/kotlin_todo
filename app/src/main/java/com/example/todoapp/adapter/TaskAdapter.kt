package com.example.todoapp.adapter


import com.example.todoapp.model.Task
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.modules.EditTaskScreen
import android.content.Intent

class TaskAdapter(private var tasks: List<Task>, private val activity: AppCompatActivity) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.task_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.task_description)
        val dueDateView: TextView = itemView.findViewById(R.id.dateview)
        val dueTimeView: TextView = itemView.findViewById(R.id.timeView)

        init {
            // Set click listener for each task item to open EditTaskScreen
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = tasks[position]
                    val intent = Intent(activity, EditTaskScreen::class.java)
                    intent.putExtra("taskId", task.id)
                    activity.startActivity(intent)
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

    // Function to update the task list and notify the adapter about the data change
    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}

