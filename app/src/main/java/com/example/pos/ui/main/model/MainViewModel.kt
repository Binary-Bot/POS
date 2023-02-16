package com.example.pos.ui.main.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import kotlin.math.abs

class MainViewModel : ViewModel() {
    private val _products = MutableLiveData<MutableList<Item>>()
    val products: MutableLiveData<MutableList<Item>> = _products
    private val db = Database()

    private val _itemsOnCart = MutableLiveData<HashMap<Item, Int>>()
    val itemsOnCart: MutableLiveData<HashMap<Item, Int>> =_itemsOnCart

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

    init{
        _products.value = db.database
        _itemsOnCart.value = hashMapOf<Item, Int>()
    }

    fun reset() {
        _totalPrice.value = 0.00
        _subtotalPrice.value = 0.00
        _tax.value = 0.00
        _itemsOnCart.value?.clear()

    }

    fun updateItem(position: Int, item: Item) {
        _products.value?.set(position, item)
    }

    fun addOnMenu(item: Item) {
        _products.value?.add(item)
    }

    fun removeFromMenu(item:Item) {
        _products.value?.remove(item)
    }

    fun checkItemOnMenu(item:Item): Boolean {
        return _products.value!!.contains(item)
    }

    fun addItemOnCart(item: Item) {
        if (_itemsOnCart.value?.containsKey(item) == true){
            _itemsOnCart.value?.put(item, _itemsOnCart.value?.get(item)!! + 1)
        } else {
            _itemsOnCart.value?.put(item, 1)
        }
        _subtotalPrice.value = (_subtotalPrice.value)?.plus(item.price)
        _tax.value = _subtotalPrice.value?.times(0.10)!!.dec()
        _totalPrice.value = (_subtotalPrice.value)?.plus(_tax.value!!)
    }

    fun removeItemOnCart(item: Item) {
        if (_itemsOnCart.value?.get(item)!! - 1 == 0){
            _itemsOnCart.value?.remove(item)
        } else {
            _itemsOnCart.value?.put(item, _itemsOnCart.value?.get(item)!! - 1)
        }
        _subtotalPrice.value = (_subtotalPrice.value)?.minus(item.price)
        _tax.value = _subtotalPrice.value?.times(0.10)!!.dec()
        _totalPrice.value = (_subtotalPrice.value)?.minus(_tax.value!!)
    }

    fun getQuantityOf(item: Item): Int {
        return _itemsOnCart.value?.get(item) ?: 0
    }

    fun calculateChange(payment: Double) {
        _change.value = abs(_totalPrice.value?.minus(payment)!!)
    }
}