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
import com.example.pos.ui.main.model.MainViewModel
import com.example.pos.ui.main.view.EditItemPopUpWindow
import com.google.android.material.card.MaterialCardView


class ItemCardAdapter(
    private val context: Context?,
    private var viewModel: MainViewModel
): RecyclerView.Adapter<ItemCardAdapter.ItemCardViewHolder>() {

    private lateinit var popView: ViewGroup

    class ItemCardViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val imageView: ImageView = view!!.findViewById(R.id.item_image)
        val nameTextView: TextView = view!!.findViewById(R.id.item_name)
        val priceTextView: TextView = view!!.findViewById(R.id.item_price)
        val cardView: MaterialCardView = view!!.findViewById(R.id.item_card_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCardViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_item, parent, false)
        popView = parent
        return ItemCardViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemCardViewHolder, position: Int) {
        context?.resources
        val item = viewModel.products.value!![position]
        holder.imageView.setImageResource(item.drawableID)
        holder.nameTextView.text = item.name
        holder.priceTextView.text = "$${item.price}"
        holder.cardView.setOnClickListener {
            EditItemPopUpWindow(this, popView, position, viewModel)
        }
    }

    override fun getItemCount(): Int = viewModel.products.value!!.size

}