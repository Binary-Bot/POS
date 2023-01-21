package com.example.pos.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.R
import com.example.pos.ui.main.model.Database
import com.google.android.material.card.MaterialCardView

class ItemCardAdapter(
    private val context: Context?,
    private var database: Database,
): RecyclerView.Adapter<ItemCardAdapter.ItemCardViewHolder>() {

    class ItemCardViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val imageView: ImageView = view!!.findViewById(R.id.item_image)
        val nameTextView: TextView = view!!.findViewById(R.id.item_name)
        val priceTextView: TextView = view!!.findViewById(R.id.item_price)
        val cardView: MaterialCardView = view!!.findViewById(R.id.item_card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCardViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_item, parent, false)
        return ItemCardViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemCardViewHolder, position: Int) {
        val resources = context?.resources
        val item = database.database[position]
        holder.imageView.setImageResource(item.drawableID)
        holder.nameTextView.text = item.name
        holder.priceTextView.text = "$${item.price}"
        holder.cardView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int = database.getSize()
}