package com.example.pos.ui.main.model


import android.util.Log
import android.view.MenuItem
import com.example.pos.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import java.io.*
import java.net.Socket

class Database {
    var database: MutableList<Item> = populateDB()
    private lateinit var db: JSONArray
    private val gson: Gson = Gson()
    private val IP = "172.16.211.183"
    private val PORT = 2028


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

    fun getItemsFromServer(): MutableList<ServerItem> {
        var items = mutableListOf<ServerItem>()

        Socket(IP, PORT).use { socket ->
            val outputStream = socket.getOutputStream()
            outputStream.write("get_items".toByteArray())
            outputStream.flush()

            val inputStream = socket.getInputStream()
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val response = bufferedReader.readLine()

            db = JSONArray(response)
            for (i in 0 until db.length()) {
                val jsonObject = db.getJSONObject(i)
                val name = jsonObject.getString("name")
                val price = jsonObject.getDouble("price")
                val imageFilename = jsonObject.getString("image")
                val menuItem = ServerItem(getImage(imageFilename), name, price)
                items.add(menuItem)
            }
        }
        return items
    }

    fun getNoImage(): String {
        val socket = Socket("172.16.23.191", 2028)
        val outputStream = socket.getOutputStream()
        outputStream.write("get_no_image".toByteArray())
        outputStream.flush()

        val inputStream = socket.getInputStream()
        val reader = BufferedReader(InputStreamReader(inputStream))
        val response = StringBuffer()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            response.append(line)
        }
        socket.close()
        Log.d("Shashwat", response.toString())
        return response.toString()
    }

    private fun getImage(image: String): String {
        val socket = Socket(IP, PORT)
        val outputStream = socket.getOutputStream()
        outputStream.write(image.toByteArray())
        outputStream.flush()

        val inputStream = socket.getInputStream()
        val byteArrayOutputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var len: Int
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteArrayOutputStream.write(buffer, 0, len)
        }
        socket.close()
        return byteArrayOutputStream.toByteArray().toString()
    }

    fun updateItemsOnServer() {
        val socket = Socket(IP, PORT)

        val outputStream = socket.getOutputStream()
        outputStream.write("update_items".toByteArray())
        outputStream.flush()
        outputStream.write(db.toString().toByteArray())
        outputStream.flush()
        outputStream.close()
        socket.close()
    }

    fun removeServerItem(item: ServerItem) {
        for (i in 0 until db.length()) {
            val obj = db.getJSONObject(i)
            if (obj.getString("name") == item.name) {
                db.remove(i)
                break
            }
        }
    }

    fun updateServerItem(position: Int, item: ServerItem) {
        val obj = db.getJSONObject(position)
        obj.put("name", item.name)
        obj.put("price", item.price)
    }
}