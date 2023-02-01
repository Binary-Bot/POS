package com.example.pos.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.pos.R
import com.example.pos.databinding.ActivityPosBinding
import com.example.pos.ui.main.adapter.ListItemAdapter
import com.example.pos.ui.main.adapter.SecItemCardAdapter
import com.example.pos.ui.main.model.Item
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
        val adapter = ListItemAdapter(requireContext(), sharedViewModel)
        binding?.cartDisplay?.cartList?.adapter = adapter
        binding?.cartDisplay?.cartList?.onItemClickListener = AdapterView.OnItemClickListener{
            parent, view, position, id ->
            sharedViewModel.removeItemOnCart(sharedViewModel.itemsOnCart.value!!.keys.elementAt(position))
            adapter.notifyDataSetChanged()
        }
        binding?.allItems?.gridRecyclerView?.adapter = SecItemCardAdapter(requireContext(), sharedViewModel, adapter)
        binding?.allItems?.gridRecyclerView?.setHasFixedSize(true)

        val subTotalTextView: TextView = binding?.cartDisplay?.subtotalTextView!!
        subTotalTextView.text = getString(R.string.subtotal_view, sharedViewModel.subtotalPrice, sharedViewModel.tax)
        sharedViewModel.subtotalPrice.observe(viewLifecycleOwner) {
                newPrice -> subTotalTextView.text = getString(R.string.subtotal_view, newPrice, sharedViewModel.tax.value) }
        sharedViewModel.tax.observe(viewLifecycleOwner) {
                newTax -> subTotalTextView.text = getString(R.string.subtotal_view, sharedViewModel.subtotalPrice.value, newTax) }

        binding?.cartDisplay?.chargeButton?.text = getString(R.string.pay, sharedViewModel.totalPrice)
        sharedViewModel.totalPrice.observe(viewLifecycleOwner) {
                newPrice -> binding?.cartDisplay?.chargeButton?.text = getString(R.string.pay, newPrice) }

        binding?.cartDisplay?.chargeButton?.setOnClickListener { payOrder() }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun payOrder(){
        Toast.makeText(activity, "Paid!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_POSActivity_to_checkoutActivity)
    }
}