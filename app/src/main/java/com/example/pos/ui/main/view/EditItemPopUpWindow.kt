package com.example.pos.ui.main.view

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import com.example.pos.R
import com.example.pos.ui.main.adapter.ItemCardAdapter
import com.example.pos.ui.main.model.Item
import com.example.pos.ui.main.model.MainViewModel
import com.example.pos.ui.main.model.ServerItem
import kotlinx.coroutines.NonDisposableHandle.parent

class EditItemPopUpWindow(
    adapter: ItemCardAdapter,
    parent: ViewGroup,
    position: Int,
    viewModel: MainViewModel): PopupWindow() {

    private var item: ServerItem =
        viewModel.products.value?.getOrElse(position) { ServerItem(viewModel.getNoImage(), "", 0.00) }!!
    init {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.edit_item, parent, false)

        contentView = layout
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT

        isFocusable = true
        layout.setBackgroundResource(androidx.appcompat.R.color.background_material_dark)
        val popupEditName: EditText = layout.findViewById(R.id.edit_text_item_name)
        popupEditName.setText(item.name)
        val popupEditPrice: EditText = layout.findViewById(R.id.edit_text_item_price)
        popupEditPrice.setText(item.price.toString())
        val removeButton:Button = layout.findViewById(R.id.remove_button)
        removeButton.setOnClickListener {
            // Do something when remove button is clicked
            viewModel.removeFromMenu(item)
            adapter.notifyItemRemoved(position)
            dismiss()
        }

        val saveButton = layout.findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            // Do something when save button is clicked
            if (viewModel.checkItemOnMenu(item)){
                viewModel.updateServerItem(position, ServerItem(item.name, popupEditName.text.toString(), popupEditPrice.text.toString().toDouble()))
                adapter.notifyItemChanged(position)
            } else {
                viewModel.addOnMenu(ServerItem(viewModel.getNoImage(), popupEditName.text.toString(), popupEditPrice.text.toString().toDouble()))
                adapter.notifyItemInserted(position)
            }

            dismiss()
        }
        showAtLocation(parent, Gravity.CENTER, 0, 0)
    }

    override fun dismiss() {
        super.dismiss()

    }
}