package com.example.pos.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.pos.R
import com.example.pos.ui.main.model.Database
import com.example.pos.ui.main.model.Item
import com.example.pos.ui.main.model.MainViewModel

class ListItemAdapter (
    private val context: Context,
    private val viewModel: MainViewModel
    ) : BaseAdapter() {

    override fun getCount(): Int {
        return viewModel.itemsOnCart.value!!.size
    }

    override fun getItem(position: Int): Item {
        return viewModel.itemsOnCart.value!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
            holder = ViewHolder(convertView)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.bind(getItem(position))
        return convertView
    }

    private inner class ViewHolder(view: View) {
        private val nameView: TextView = view.findViewById(R.id.name_cart)
        private val priceView: TextView = view.findViewById(R.id.price_cart)
        private val quantityView: TextView = view.findViewById(R.id.quantity_cart)

        fun bind(data: Item) {
            nameView.text = data.name
            priceView.text = "$${data.price}"
            quantityView.text = "Quantity: 1"
        }
    }
}