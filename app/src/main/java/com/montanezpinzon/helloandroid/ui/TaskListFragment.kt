package com.montanezpinzon.helloandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.montanezpinzon.helloandroid.databinding.FragmentTaskListBinding
import com.montanezpinzon.helloandroid.viewmodel.TaskViewModel

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by activityViewModels()

    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadTasks()
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter { task ->
            val action = TaskListFragmentDirections.actionTaskListFragmentToTaskDetailFragment(task.id)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@TaskListFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.tasks.observe(viewLifecycleOwner) { taskList ->
            binding.textViewTaskCount.text = "Total tareas: ${taskList.size}"
            adapter.submitList(taskList)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setupClickListeners() {
        binding.buttonAddTask.setOnClickListener {
            val action = TaskListFragmentDirections.actionTaskListFragmentToTaskDetailFragment(-1)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}