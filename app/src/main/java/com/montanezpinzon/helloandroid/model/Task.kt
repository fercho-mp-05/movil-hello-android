package com.montanezpinzon.helloandroid.model

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val hasReminder: Boolean,
    val reminderTime: String
)