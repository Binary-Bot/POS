package com.example.pos.ui.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pos.databinding.ActivityEmployeeBinding
import com.example.pos.ui.main.adapter.ItemCardAdapter
import com.example.pos.ui.main.model.Database

class EmployeeActivity(): AppCompatActivity() {
    private lateinit var binding: ActivityEmployeeBinding
    private var db = Database()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listAllItems.gridRecyclerView.adapter = ItemCardAdapter(applicationContext, db)
        binding.listAllItems.gridRecyclerView.setHasFixedSize(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}