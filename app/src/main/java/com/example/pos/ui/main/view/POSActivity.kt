package com.example.pos.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pos.databinding.ActivityPosBinding
import com.example.pos.ui.main.adapter.ListItemAdapter
import com.example.pos.ui.main.adapter.SecItemCardAdapter
import com.example.pos.ui.main.model.Database
import com.example.pos.ui.main.model.MainViewModel

class POSActivity(): Fragment() {

    private var binding:ActivityPosBinding? = null
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragBinding = ActivityPosBinding.inflate(inflater, container, false)
        binding=fragBinding
        return fragBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply{
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            posActivity = this@POSActivity
        }
        binding?.cartDisplay?.cartList?.adapter=ListItemAdapter(requireContext(), sharedViewModel)
        binding?.allItems?.gridRecyclerView?.adapter = SecItemCardAdapter(requireContext(), sharedViewModel)
        binding?.allItems?.gridRecyclerView?.setHasFixedSize(true)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}