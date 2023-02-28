package com.example.pos.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pos.R
import com.example.pos.ui.main.model.MainViewModel
import com.example.pos.ui.main.model.ServerItem

class ListItemAdapter (
    private val context: Context,
    private val viewModel: MainViewModel,
    ) : BaseAdapter() {

    override fun getCount(): Int {
        return viewModel.itemsOnCart.value?.size ?: 0
    }

    override fun getItem(position: Int): ServerItem {
        return viewModel.itemsOnCart.value!!.keys.elementAt(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var cView = convertView
        val holder: ViewHolder

        if (cView == null) {
            cView = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
            holder = ViewHolder(cView)
            cView.tag = holder
        } else {
            holder = cView.tag as ViewHolder
        }
        holder.bind(getItem(position))
        return cView
    }

    private inner class ViewHolder(view: View) {
        private val nameView: TextView = view.findViewById(R.id.name_cart)
        private val priceView: TextView = view.findViewById(R.id.price_cart)
        private val quantityView: TextView = view.findViewById(R.id.quantity_cart)

        fun bind(data: ServerItem) {
            val quantity = viewModel.getQuantityOf(data)
            nameView.text = data.name
            priceView.text = "$${data.price * quantity}"
            quantityView.text = "Quantity: ${quantity}"
        }
    }
}