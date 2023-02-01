package com.example.pos.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pos.R
import com.example.pos.databinding.ActivityCheckoutBinding
import com.example.pos.ui.main.adapter.ListItemAdapter
import com.example.pos.ui.main.model.MainViewModel

class CheckoutActivity : Fragment(){

    private lateinit var binding: ActivityCheckoutBinding
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragBinding = ActivityCheckoutBinding.inflate(inflater, container, false)
        binding=fragBinding
        return fragBinding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply{
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            checkoutActivity = this@CheckoutActivity
        }

        val adapter = ListItemAdapter(requireContext(), sharedViewModel)
        binding.cartDisplay.cartList.adapter = adapter

        binding.cartDisplay.subtotalTextView.text = getString(R.string.subtotal_view, sharedViewModel.subtotalPrice, sharedViewModel.tax)
        binding.cartDisplay.chargeButton.text = "Total: ${sharedViewModel.totalPrice}"

    }

}