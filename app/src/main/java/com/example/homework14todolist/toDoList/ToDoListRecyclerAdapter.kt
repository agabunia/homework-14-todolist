package com.example.homework14todolist.toDoList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework14todolist.databinding.TaskLayoutBinding

class ToDoListRecyclerAdapter :
    ListAdapter<Task, ToDoListRecyclerAdapter.ToDoListViewHolder>(TaskDIffUtil()) {

    class TaskDIffUtil : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ToDoListViewHolder(TaskLayoutBinding.inflate(inflate, parent, false))
    }

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        holder.bind()
    }

    inner class ToDoListViewHolder(private val binding: TaskLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var task: Task

        fun bind() {
            task = currentList[adapterPosition]
            with(binding) {
                tvTask.text = task.task
                btnDeleteTask.setOnClickListener {
                    clickListeners?.deleteBtn(task.id)
                }
                btnEditTask.setOnClickListener {
                    clickListeners?.editBtn(task.id)
                }
            }
        }
    }

    var clickListeners: ClickListeners? = null

    interface ClickListeners {
        fun deleteBtn(position: Int)
        fun editBtn(position: Int)
    }
}