package com.example.pos.ui.main.model

import androidx.annotation.DrawableRes


data class Item(
    @DrawableRes val drawableID: Int,
    val name: String,
    val price: Double
)
