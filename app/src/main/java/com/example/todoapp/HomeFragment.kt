import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.todoapp.adapter.TaskAdapter
import com.example.todoapp.databinding.ActivityAddNoteBinding
import com.example.todoapp.databinding.FragmentMainBinding
import com.example.todoapp.model.Task
import com.example.todoapp.viewModel.TaskViewModel
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.core.graphics.drawable.toDrawable

class HomeFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var tasks: List<Task> = emptyList()

    // Используем биндинг фрагмента
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun refreshTasks() {
        taskViewModel.getAllTasks { taskList ->
            tasks = taskList
            taskAdapter.updateTasks(tasks)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация элементов UI
        binding.addNewBtn.setOnClickListener {
            showAddTaskDialog()
        }
        binding.refreshFab.setOnClickListener {
            refreshTasks()
        }
        // Настройка ViewModel
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)


        taskAdapter = TaskAdapter(
            tasks = tasks,
            context = requireContext() // make sure this is not null
        )

        recyclerView = binding.recyclerView
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // 🔹 Add FAKE tasks to test UI
        val fakeTasks = listOf(
            Task(taskTitle = "Buy groceries", taskDescription = "Milk, Eggs, Bread", dueDate = "16/05/2025", dueTime = "10:00"),
            Task(taskTitle = "Call Mom", taskDescription = "Weekly call", dueDate = "16/05/2025", dueTime = "14:00"),
            Task(taskTitle = "Meeting", taskDescription = "Project kickoff", dueDate = "17/05/2025", dueTime = "09:30")
        )
//        taskAdapter.updateTasks(fakeTasks)

        // Загрузка задач
            taskViewModel.getAllTasks { taskList ->
            tasks = taskList
         //       tasks.apply(fakeTasks)
            taskAdapter.updateTasks(tasks)
        }
    }

    private fun showAddTaskDialog() {
        val dialog = AddTaskDialogFragment().apply {
            setOnTaskAddedListener { newTask ->
                taskViewModel.insertTask(newTask)
            }
        }
        dialog.show(parentFragmentManager, "AddTaskDialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class AddTaskDialogFragment : DialogFragment() {
    private var listener: ((Task) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ActivityAddNoteBinding.inflate(inflater, container, false)

        // Настройка прозрачности фона
        dialog?.window?.apply {
            // Уменьшаем прозрачность для лучшей видимости
            setBackgroundDrawable(Color.BLACK.toDrawable())
//            setBackgroundDrawable(ColorDrawable(Color.argb(200, 0, 0, 0)))
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        // Улучшаем видимость элементов
        binding.textView4.apply {
            setTextColor(Color.BLUE)
            setTextSize(24f)
        }

        // Настройка полей ввода
        binding.taskTitle.apply {
            setTextColor(Color.BLUE)
            highlightColor = Color.GREEN
        }

        binding.taskDescription.apply {
            setTextColor(Color.BLUE)
            highlightColor = Color.GREEN
        }

        // Добавляем выбор даты
        binding.textViewDueDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = Calendar.getInstance().apply {
                        set(selectedYear, selectedMonth, selectedDay)
                    }

                    binding.textViewDueDate.setText(
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.time)
                    )
                },
                year,
                month,
                day
            ).show()
        }

        binding.doneBtn.setOnClickListener {
            val newTask = Task(
                taskTitle = binding.taskTitle.text.toString(),
                taskDescription = binding.taskDescription.text.toString(),
                dueTime = binding.textViewDueTime.text.toString(),
                dueDate = binding.textViewDueDate.text.toString()
            )
            listener?.invoke(newTask)
            dismiss()
        }

        return binding.root
    }

    fun setOnTaskAddedListener(listener: (Task) -> Unit) {
        this.listener = listener
    }
}
class AddTaskDialogFragment2 : DialogFragment() {
    private var listener: ((Task) -> Unit)? = null

    fun setOnTaskAddedListener(listener: (Task) -> Unit) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ActivityAddNoteBinding.inflate(inflater, container, false)

        binding.doneBtn.setOnClickListener {
            val newTask = Task(
                taskTitle = binding.textView4.text.toString(),
                taskDescription  = binding.taskDescription.text.toString(),
                dueTime = binding.textViewDueTime.text.toString(),
                dueDate = binding.textViewDueDate.text.toString()
            )
            listener?.invoke(newTask)
            dismiss()
        }

        return binding.root
    }
}