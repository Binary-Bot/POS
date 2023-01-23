package com.example.pos.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pos.R
import com.example.pos.databinding.ActivityStartBinding

class StartActivity: Fragment() {
    private var binding: ActivityStartBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragBinding = ActivityStartBinding.inflate(inflater, container, false)
        binding = fragBinding
        return fragBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.startActivity = this
        binding?.empButton?.setOnClickListener {
            findNavController().navigate(R.id.action_startActivity_to_employeeActivity)
        }
        binding?.custButton?.setOnClickListener {
            findNavController().navigate(R.id.action_startActivity_to_POSActivity)
        }
    }
}