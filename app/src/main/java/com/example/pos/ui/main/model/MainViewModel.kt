package com.example.pos.ui.main.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat

class MainViewModel : ViewModel() {
    private val _products = MutableLiveData<MutableList<Item>>()
    val products: LiveData<MutableList<Item>> = _products
    private val db = Database()

    private val _itemsOnCart = MutableLiveData<MutableList<Item>>()
    val itemsOnCart: MutableLiveData<MutableList<Item>> =_itemsOnCart

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<String> = Transformations.map(_totalPrice) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    init{
        _products.value = db.database
        _itemsOnCart.value = mutableListOf<Item>()
        _totalPrice.value = 0.00
    }

    fun removeFromMenu(item:Item) {
        _products.value?.remove(item)
    }

    fun addItemOnCart(item: Item) {
        Log.d("Alert", "Item added on cart in viewModel")
        _itemsOnCart.value?.add(item)
        _totalPrice.value = (_totalPrice.value)?.plus(item.price)
        Log.d("Alert", _itemsOnCart.value.toString())
    }

    fun removeItemOnCart(item: Item) {
        _itemsOnCart.value?.remove(item)
        _totalPrice.value = (_totalPrice.value)?.minus(item.price)
    }
}