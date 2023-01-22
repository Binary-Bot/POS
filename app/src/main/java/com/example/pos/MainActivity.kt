package com.example.pos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pos.databinding.ActivityMainBinding
import com.example.pos.ui.main.view.EmployeeActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commitNow()
//        }
        binding.empButton.setOnClickListener {
            listIntent = Intent(this, EmployeeActivity::class.java)
            startActivity(listIntent)
        }
        binding.custButton.setOnClickListener {
            listIntent = Intent()
            startActivity(listIntent)
        }



    }
}