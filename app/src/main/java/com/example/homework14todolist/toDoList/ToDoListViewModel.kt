package com.example.homework14todolist.toDoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ToDoListViewModel : ViewModel() {

    private val tasks = mutableListOf(Task(1, "task 1"), Task(2, "task 2"))
    private val _taskFlow = MutableStateFlow<List<Task>>(tasks)
    val taskFlow: SharedFlow<List<Task>> = _taskFlow.asStateFlow()

    fun addNewTask(newTask: String) {
        viewModelScope.launch {
            _taskFlow.value = _taskFlow.value.toMutableList().also {
                it.add(Task(it.size + 1, newTask))
            }
        }
    }

    fun deleteTask(taskID: Int) {
        viewModelScope.launch {
            _taskFlow.value = _taskFlow.value.toMutableList().filterNot {
                it.id == taskID
            }
        }
    }

    fun editTask(taskID: Int, editedTask: String) {
        viewModelScope.launch {
            val allTasks = _taskFlow.value.toMutableList()
            val taskIndex = allTasks.indexOfFirst { it.id == taskID }

            if (taskIndex != -1) {
                allTasks[taskIndex] = allTasks[taskIndex].copy(task = editedTask)
                _taskFlow.value = allTasks.toList()
            }
        }
    }
}