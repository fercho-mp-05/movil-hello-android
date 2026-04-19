package com.montanezpinzon.helloandroid.repository

import android.content.Context
import android.content.SharedPreferences
import com.montanezpinzon.helloandroid.model.Task

class TaskRepository(context: Context) {

    companion object {
        private const val PREFS_NAME = "tasks_prefs"
        private const val KEY_TASK_COUNT = "task_count"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private var tasks: MutableList<Task> = loadTasksFromPrefs()

    fun getAllTasks(): List<Task> {
        return tasks.toList()
    }

    fun getTaskById(id: Int): Task? {
        return tasks.find { it.id == id }
    }

    fun addTask(task: Task) {
        tasks.add(task)
        saveTasksToPrefs()
    }

    fun updateTask(task: Task) {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            tasks[index] = task
            saveTasksToPrefs()
        }
    }

    fun deleteTask(taskId: Int) {
        tasks.removeAll { it.id == taskId }
        saveTasksToPrefs()
    }

    private fun loadTasksFromPrefs(): MutableList<Task> {
        val loadedTasks = mutableListOf<Task>()
        val count = prefs.getInt(KEY_TASK_COUNT, 0)

        for (i in 0 until count) {
            val id = prefs.getInt("task_${i}_id", -1)
            val title = prefs.getString("task_${i}_title", "") ?: ""
            val description = prefs.getString("task_${i}_description", "") ?: ""
            val hasReminder = prefs.getBoolean("task_${i}_hasReminder", false)
            val reminderTime = prefs.getString("task_${i}_reminderTime", "") ?: ""

            if (id != -1) {
                loadedTasks.add(
                    Task(
                        id = id,
                        title = title,
                        description = description,
                        hasReminder = hasReminder,
                        reminderTime = reminderTime
                    )
                )
            }
        }

        return loadedTasks
    }

    private fun saveTasksToPrefs() {
        val editor = prefs.edit()
        editor.clear()

        editor.putInt(KEY_TASK_COUNT, tasks.size)

        tasks.forEachIndexed { index, task ->
            editor.putInt("task_${index}_id", task.id)
            editor.putString("task_${index}_title", task.title)
            editor.putString("task_${index}_description", task.description)
            editor.putBoolean("task_${index}_hasReminder", task.hasReminder)
            editor.putString("task_${index}_reminderTime", task.reminderTime)
        }

        editor.apply()
    }
}