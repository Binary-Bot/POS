package com.example.pos.ui.main.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pos.R
import com.example.pos.databinding.ActivityCheckoutBinding
import com.example.pos.ui.main.adapter.ListItemAdapter
import com.example.pos.ui.main.model.MainViewModel
import java.io.File

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

        binding.cartDisplay.subtotalTextView.text = getString(R.string.subtotal_view, sharedViewModel.subtotalPrice.value, sharedViewModel.tax.value)
        binding.cartDisplay.chargeButton.text = "Total: ${sharedViewModel.totalPrice.value}"

        binding.checkoutDisplay.editTextTotalPaid.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    sharedViewModel.calculateChange(s.toString().toDouble())
                }
            }
        })
        sharedViewModel.change.observe(viewLifecycleOwner) {
            newChange -> binding.checkoutDisplay.changeTextView.text = sharedViewModel.change.value
        }

        binding.checkoutDisplay.sendEmailButton.setOnClickListener {
            val email = binding.checkoutDisplay.editTextEmail.text.toString()
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, "Receipt")
                putExtra(Intent.EXTRA_TEXT, sharedViewModel.generateReceipt())
            }
            startActivity(intent)
        }

        binding.checkoutDisplay.newOrderButton.setOnClickListener {
            sharedViewModel.reset()
            findNavController().navigate(R.id.action_checkoutActivity_to_POSActivity)
        }


    }

}