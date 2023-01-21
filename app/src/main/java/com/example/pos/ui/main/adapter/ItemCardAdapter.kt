package com.example.pos.ui.main.adapter

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pos.R
import com.example.pos.ui.main.model.Database
import com.example.pos.ui.main.view.EditItemPopUpWindow
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.NonDisposableHandle.parent

class ItemCardAdapter(
    private val context: Context?,
    private var database: Database,
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
        val resources = context?.resources
        val item = database.database[position]
        holder.imageView.setImageResource(item.drawableID)
        holder.nameTextView.text = item.name
        holder.priceTextView.text = "$${item.price}"
        holder.cardView.setOnClickListener {
            val popupWindow = EditItemPopUpWindow(popView, item)
        }
    }

    override fun getItemCount(): Int = database.getSize()
}