package com.example.pos.ui.main.model

import com.example.pos.R

class Database {
    private fun populateDB(): MutableList<Item> {
        return mutableListOf(
            Item("California Roll", 6.50),
            Item("Spicy Tuna Roll", 7.75),
            Item("Unagi Roll", 7.75),
            Item("Shrimp Tempura Roll", 9.95),
            Item("Crunchy Roll", 12.00),
            Item("Monster Roll", 12.50),
            Item("Dragon Roll", 14.50),
            Item("Black Dragon Roll", 15.00),
            Item("Diamond Roll", 15.50),
            Item("Jumbo Roll", 16.50)

        )
    }
}