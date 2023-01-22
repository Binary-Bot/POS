package com.example.pos.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pos.R
import com.example.pos.ui.main.model.Database
import com.example.pos.ui.main.model.Item

class ListItemAdapter (private val context: Context, private val data: Database) : BaseAdapter() {

    override fun getCount(): Int {
        return data.getSize()
    }

    override fun getItem(position: Int): Any {
        return data.database[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
            holder = ViewHolder(convertView)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.bind(data.database[position])
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