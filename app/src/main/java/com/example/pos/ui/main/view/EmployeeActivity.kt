package com.example.pos.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pos.databinding.ActivityEmployeeBinding
import com.example.pos.ui.main.adapter.ItemCardAdapter
import com.example.pos.ui.main.model.MainViewModel
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class EmployeeActivity(): Fragment(){
    private var binding: ActivityEmployeeBinding? = null
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragBinding = ActivityEmployeeBinding.inflate(inflater, container, false)
        binding=fragBinding
        return fragBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply{
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            employeeActivity= this@EmployeeActivity
        }
        binding?.listAllItems?.gridRecyclerView?.adapter = ItemCardAdapter(requireContext(), sharedViewModel)
        binding?.listAllItems?.gridRecyclerView?.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}