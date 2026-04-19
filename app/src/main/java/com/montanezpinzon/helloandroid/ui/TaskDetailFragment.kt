package com.montanezpinzon.helloandroid.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.montanezpinzon.helloandroid.databinding.FragmentTaskDetailBinding
import com.montanezpinzon.helloandroid.receiver.TaskReminderReceiver
import com.montanezpinzon.helloandroid.viewmodel.TaskViewModel
import java.util.Calendar
import java.util.Locale

class TaskDetailFragment : Fragment() {

    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by activityViewModels()
    private val args: TaskDetailFragmentArgs by navArgs()

    private var editingTaskId: Int = -1
    private var selectedHour: Int = -1
    private var selectedMinute: Int = -1
    private var reminderTimeText: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editingTaskId = args.taskId

        if (editingTaskId != -1) {
            loadTaskData(editingTaskId)
        }

        setupClickListeners()
    }

    private fun loadTaskData(taskId: Int) {
        val task = viewModel.getTaskById(taskId)
        task?.let {
            binding.editTextTitle.setText(it.title)
            binding.editTextDescription.setText(it.description)
            binding.switchReminder.isChecked = it.hasReminder
            reminderTimeText = it.reminderTime

            if (it.reminderTime.isNotEmpty()) {
                binding.textViewReminderTime.text = "Hora del recordatorio: ${it.reminderTime}"

                val parts = it.reminderTime.split(":")
                if (parts.size == 2) {
                    selectedHour = parts[0].toIntOrNull() ?: -1
                    selectedMinute = parts[1].toIntOrNull() ?: -1
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.buttonSelectTime.setOnClickListener {
            showTimePicker()
        }

        binding.buttonSave.setOnClickListener {
            saveTask()
        }

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour =
            if (selectedHour != -1) selectedHour else calendar.get(Calendar.HOUR_OF_DAY)
        val minute =
            if (selectedMinute != -1) selectedMinute else calendar.get(Calendar.MINUTE)

        val dialog = TimePickerDialog(
            requireContext(),
            { _, pickedHour, pickedMinute ->
                selectedHour = pickedHour
                selectedMinute = pickedMinute
                reminderTimeText = String.format(
                    Locale.getDefault(),
                    "%02d:%02d",
                    pickedHour,
                    pickedMinute
                )
                binding.textViewReminderTime.text =
                    "Hora del recordatorio: $reminderTimeText"
                binding.textViewReminderTime.error = null
            },
            hour,
            minute,
            true
        )

        dialog.show()
    }

    private fun saveTask() {
        val title = binding.editTextTitle.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        val hasReminder = binding.switchReminder.isChecked

        if (title.isEmpty()) {
            binding.editTextTitle.error = "Ingrese un título"
            return
        }

        if (hasReminder && reminderTimeText.isEmpty()) {
            binding.textViewReminderTime.error = "Seleccione una hora"
            return
        }

        if (editingTaskId == -1) {
            val newTask = viewModel.addTask(
                title = title,
                description = description,
                hasReminder = hasReminder,
                reminderTime = reminderTimeText
            )

            if (hasReminder) {
                scheduleReminder(
                    taskId = newTask.id,
                    title = newTask.title,
                    description = newTask.description,
                    hour = selectedHour,
                    minute = selectedMinute
                )
            }
        } else {
            viewModel.updateTask(
                id = editingTaskId,
                title = title,
                description = description,
                hasReminder = hasReminder,
                reminderTime = reminderTimeText
            )

            if (hasReminder) {
                scheduleReminder(
                    taskId = editingTaskId,
                    title = title,
                    description = description,
                    hour = selectedHour,
                    minute = selectedMinute
                )
            }
        }

        findNavController().navigateUp()
    }

    private fun scheduleReminder(
        taskId: Int,
        title: String,
        description: String,
        hour: Int,
        minute: Int
    ) {
        val context = requireContext()

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        val intent = Intent(context, TaskReminderReceiver::class.java).apply {
            putExtra("task_title", title)
            putExtra("task_description", description)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            taskId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}