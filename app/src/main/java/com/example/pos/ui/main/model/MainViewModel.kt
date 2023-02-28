package com.example.pos.ui.main.model

import android.util.Log
import androidx.lifecycle.*
import java.text.NumberFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val db = Database()
    private val _products = MutableLiveData<MutableList<ServerItem>>()
    val products: MutableLiveData<MutableList<ServerItem>> = _products

    private val _itemsOnCart = MutableLiveData<HashMap<ServerItem, Int>>()
    val itemsOnCart: MutableLiveData<HashMap<ServerItem, Int>> =_itemsOnCart

    private val _subtotalPrice = MutableLiveData<Double>(0.00)
    val subtotalPrice: LiveData<String> = Transformations.map(_subtotalPrice) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    private val _tax = MutableLiveData<Double>(0.00)
    val tax: LiveData<String> = Transformations.map(_tax) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    private val _totalPrice = MutableLiveData<Double>(0.00)
    val totalPrice: LiveData<String> = Transformations.map(_totalPrice) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    private val _change = MutableLiveData<Double>(0.00)
    val change: LiveData<String> = Transformations.map(_change) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    private lateinit var noImageString: String

    init{
        getItemsFromServer()
        _itemsOnCart.value = hashMapOf<ServerItem, Int>()
    }

    private fun getItemsFromServer(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Shashwat", "Starting to get items")
            try {
                val items = db.getItemsFromServer()
                _products.postValue(items)
            } catch (e: Exception) {
                Log.e("Shashwat", e.message.toString())
                Log.e("Shashwat", "Error getting items from server")
            }
        }
        Log.d("Shashwat", "server function ended")
    }

    fun updateItemsOnServer(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.updateItemsOnServer()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error updating list on server", e)
            }
        }
    }

    fun reset() {
        _totalPrice.value = 0.00
        _subtotalPrice.value = 0.00
        _tax.value = 0.00
        _change.value = 0.00
        _itemsOnCart.value?.clear()

    }

    fun updateServerItem(position: Int, item: ServerItem) {
        _products.value?.set(position, item)
        db.updateServerItem(position, item)
        updateItemsOnServer()
    }

    fun addOnMenu(item: ServerItem) {
        _products.value?.add(item)
    }

    fun removeFromMenu(item:ServerItem) {
        _products.value?.remove(item)
        db.removeServerItem(item)
        updateItemsOnServer()
    }

    fun checkItemOnMenu(item:ServerItem): Boolean {
        return _products.value!!.contains(item)
    }

    fun addItemOnCart(item: ServerItem) {
        if (_itemsOnCart.value?.containsKey(item) == true){
            _itemsOnCart.value?.put(item, _itemsOnCart.value?.get(item)!! + 1)
        } else {
            _itemsOnCart.value?.put(item, 1)
        }
        _subtotalPrice.value = (_subtotalPrice.value)?.plus(item.price)
        _tax.value = _subtotalPrice.value?.times(0.10)!!.dec()
        _totalPrice.value = (_subtotalPrice.value)?.plus(_tax.value!!)
    }

    fun removeItemOnCart(item: ServerItem) {
        if (_itemsOnCart.value?.get(item)!! - 1 == 0){
            _itemsOnCart.value?.remove(item)
        } else {
            _itemsOnCart.value?.put(item, _itemsOnCart.value?.get(item)!! - 1)
        }
        _subtotalPrice.value = (_subtotalPrice.value)?.minus(item.price)
        _tax.value = _subtotalPrice.value?.times(0.10)!!.dec()
        _totalPrice.value = (_subtotalPrice.value)?.minus(_tax.value!!)
    }

    fun getQuantityOf(item: ServerItem): Int {
        return _itemsOnCart.value?.get(item) ?: 0
    }

    fun calculateChange(payment: Double) {
        _change.value = _totalPrice.value?.minus(payment)!!
    }

    fun getNoImage(): String {
        return noImageString
    }

    fun generateReceipt(): String {
        val builder = StringBuilder()
        builder.append("Receipt\n\n")

        for ((item, quantity) in _itemsOnCart.value.orEmpty()) {
            builder.append("${item.name}          x $quantity            ${NumberFormat.getCurrencyInstance().format(item.price * quantity)}\n")
        }

        builder.append("\nSubtotal:                     ${subtotalPrice.value}\n")
        builder.append("Tax:                         ${tax.value}\n")
        builder.append("Total:                       ${totalPrice.value}\n")

        return builder.toString()
    }
}