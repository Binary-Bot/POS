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
import com.example.pos.ui.main.model.Item
import kotlinx.coroutines.NonDisposableHandle.parent

class EditItemPopUpWindow(parent: ViewGroup, item: Item): PopupWindow() {

    init {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.edit_item, parent, false)

        contentView = layout
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT

        isFocusable = true
        layout.setBackgroundResource(R.color.white)
        val popupEditName: EditText = layout.findViewById(R.id.editText_item_name)
        popupEditName.setText(item.name)
        val popupEditPrice: EditText = layout.findViewById(R.id.editText_item_price)
        popupEditPrice.setText(item.price.toString())
        val removeButton:Button = layout.findViewById(R.id.remove_button)
        removeButton.setOnClickListener {
            // Do something when remove button is clicked
            dismiss()
        }

        val saveButton = layout.findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            // Do something when save button is clicked
            dismiss()
        }
        Log.d("PopUp", "Window created")
        showAtLocation(parent, Gravity.CENTER, 0, 0)
    }
}