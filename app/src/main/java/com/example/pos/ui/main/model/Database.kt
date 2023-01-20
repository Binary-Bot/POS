package com.example.pos.ui.main.model

import com.example.pos.R

class Database {
    private var database: MutableList<Item> = populateDB()

    private fun populateDB(): MutableList<Item> {
        return mutableListOf(
            Item(R.drawable.californiaroll,"California Roll", 6.50),
            Item(R.drawable.spicytunaroll,"Spicy Tuna Roll", 7.75),
            Item(R.drawable.unagiroll,"Unagi Roll", 7.75),
            Item(R.drawable.shrimptempura,"Shrimp Tempura Roll", 9.95),
            Item(R.drawable.crunchyroll,"Crunchy Roll", 12.00),
            Item(R.drawable.monsterroll,"Monster Roll", 12.50),
            Item(R.drawable.dragonroll,"Dragon Roll", 14.50),
            Item(R.drawable.blackdragonroll,"Black Dragon Roll", 15.00),
            Item(R.drawable.diamondroll,"Diamond Roll", 15.50),
            Item(R.drawable.jumboroll,"Jumbo Roll", 16.50)
        )
    }

    fun addItem(item: Item) {
        database.add(item)
    }

    fun removeItem(item: Item) {
        database.remove(item)
    }
}