package com.montanezpinzon.helloandroid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.montanezpinzon.helloandroid.model.Task
import com.montanezpinzon.helloandroid.repository.TaskRepository

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TaskRepository(application.applicationContext)

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    private val _selectedTask = MutableLiveData<Task?>()
    val selectedTask: LiveData<Task?> = _selectedTask

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadTasks()
    }

    fun loadTasks() {
        _isLoading.value = true
        _tasks.value = repository.getAllTasks()
        _isLoading.value = false
    }

    fun getTaskById(id: Int): Task? {
        return repository.getTaskById(id)
    }

    fun selectTask(task: Task) {
        _selectedTask.value = task
    }

    fun addTask(
        title: String,
        description: String,
        hasReminder: Boolean,
        reminderTime: String
    ): Task {
        val newId = (repository.getAllTasks().maxOfOrNull { it.id } ?: 0) + 1

        val newTask = Task(
            id = newId,
            title = title,
            description = description,
            hasReminder = hasReminder,
            reminderTime = reminderTime
        )

        repository.addTask(newTask)
        loadTasks()
        return newTask
    }

    fun updateTask(
        id: Int,
        title: String,
        description: String,
        hasReminder: Boolean,
        reminderTime: String
    ) {
        val updatedTask = Task(
            id = id,
            title = title,
            description = description,
            hasReminder = hasReminder,
            reminderTime = reminderTime
        )

        repository.updateTask(updatedTask)
        loadTasks()
    }

    fun deleteTask(taskId: Int) {
        repository.deleteTask(taskId)
        loadTasks()
    }
}