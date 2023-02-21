package com.example.pos.ui.main.model

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.pos.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*
import java.net.Socket
class Database {
    var database: MutableList<Item> = populateDB()
    private val gson = Gson()


    private fun populateDB(): MutableList<Item> {
        return mutableListOf(
            Item(R.drawable.californiaroll, "California Roll", 6.50),
            Item(R.drawable.spicytunaroll, "Spicy Tuna Roll", 7.75),
            Item(R.drawable.unagiroll, "Unagi Roll", 7.75),
            Item(R.drawable.shrimptempura, "Shrimp Tempura Roll", 9.75),
            Item(R.drawable.crunchyroll, "Crunchy Roll", 12.00),
            Item(R.drawable.monsterroll, "Monster Roll", 12.50),
            Item(R.drawable.dragonroll, "Dragon Roll", 14.50),
            Item(R.drawable.blackdragonroll, "Black Dragon Roll", 15.00),
            Item(R.drawable.diamondroll, "Diamond Roll", 15.50),
            Item(R.drawable.jumboroll, "Jumbo Roll", 16.50)
        )
    }

    fun getSize(): Int {
        return database.size
    }

    fun addItem(item: Item) {
        database.add(item)
    }

    fun removeItem(item: Item) {
        database.remove(item)
    }

    fun getItemsFromServer(): List<Item> {
        val socket = Socket("172.16.217.231", 2028)
        val outputStream = socket.getOutputStream()
        outputStream.write("get_items".toByteArray())
        outputStream.flush()

        val inputStream = socket.getInputStream()
        val reader = BufferedReader(InputStreamReader(inputStream))
        val response = StringBuffer()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            response.append(line)
        }
        socket.close()
        val type = object: TypeToken<List<ServerItem>>(){}.type
        return gson.fromJson(response.toString(), type)
    }
}