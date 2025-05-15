/*
package com.example.todoapp.modules

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.adapter.TaskAdapter
import com.example.todoapp.databinding.ActivityHomeBinding
import com.example.todoapp.model.Task
import com.example.todoapp.viewModel.TaskViewModel

class HomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var tasks: List<Task> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
       binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addNewBtn.setOnClickListener {
            startActivity(Intent(this, AddNewTaskScreen::class.java))
        }

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        taskViewModel.getAllTasks { taskList ->
            tasks = taskList
            updateUI(tasks)
        }

        binding.searchTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filterTasks(s.toString())
            }
        })
        &

    }

    private fun updateUI(tasks: List<Task>) {
       /* recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(tasks, this)
        recyclerView.adapter = taskAdapter

        if (tasks.isEmpty()) {
            binding.emptyView.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.emptyView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }*/
    }

    private fun filterTasks(query: String) {
        val filteredTasks = tasks.filter {
            it.taskTitle.contains(query, ignoreCase = true) || it.taskDescription.contains(query, ignoreCase = true)
        }
        taskAdapter.updateTasks(filteredTasks)
    }
}
 */