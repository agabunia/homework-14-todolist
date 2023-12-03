package com.example.homework14todolist.toDoList

import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework14todolist.BaseFragment
import com.example.homework14todolist.databinding.FragmentToDoListBinding
import kotlinx.coroutines.launch

class ToDoListFragment : BaseFragment<FragmentToDoListBinding>(FragmentToDoListBinding::inflate),
    ToDoListRecyclerAdapter.ClickListeners {

    private lateinit var taskAdapter: ToDoListRecyclerAdapter
    private val viewModel: ToDoListViewModel by viewModels()

    override fun setUp() {
        setTaskAdapter()
    }

    override fun setListeners() {
        binding.btnAddTask.setOnClickListener {
            addTaskButton()
        }
    }

    override fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.taskFlow.collect {
                    taskAdapter.submitList(it)
                }
            }
        }
    }

    override fun refresher() {
        binding.swipeToRefresh.setOnRefreshListener {
            binding.swipeToRefresh.isRefreshing = false
        }
    }

    private fun setTaskAdapter() {
        taskAdapter = ToDoListRecyclerAdapter()
        taskAdapter.clickListeners = this
        with(binding) {
            tasksRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
            tasksRecycler.adapter = taskAdapter
        }
    }

    private fun addTaskButton() {
        val userInput = binding.etInputTask.text.toString()
        if (userInput.isNotEmpty()) {
            viewModel.addNewTask(userInput)
            binding.etInputTask.text?.clear()
            d("DeleteButton", "true")
        }
    }

    override fun deleteBtn(position: Int) {
        viewModel.deleteTask(position)
    }

    override fun editBtn(position: Int) {
        val userInput = binding.etInputTask.text.toString()
        if (userInput.isNotEmpty()) {
            viewModel.editTask(position, userInput)
            binding.etInputTask.text?.clear()
        }
    }
}