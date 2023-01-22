package com.example.pos.ui.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pos.databinding.ActivityEmployeeBinding
import com.example.pos.databinding.ActivityPosBinding
import com.example.pos.ui.main.adapter.ItemCardAdapter
import com.example.pos.ui.main.adapter.ListItemAdapter
import com.example.pos.ui.main.model.Database

class POSActivity: AppCompatActivity() {

    private lateinit var binding:ActivityPosBinding
    private var db = Database()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.allItems.gridRecyclerView.adapter = ItemCardAdapter(applicationContext, db)
        binding.allItems.gridRecyclerView.setHasFixedSize(true)

        binding.cartDisplay.cartList.adapter=ListItemAdapter(applicationContext, db)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}