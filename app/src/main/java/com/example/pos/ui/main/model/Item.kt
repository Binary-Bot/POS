package com.example.pos.ui.main.model

import androidx.annotation.StringRes

data class Item(
    @StringRes val stringID: Int,
    val price: Double
)
