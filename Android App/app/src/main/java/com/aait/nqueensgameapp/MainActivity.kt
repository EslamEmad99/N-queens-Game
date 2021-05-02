package com.aait.nqueensgameapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.aait.nqueensgameapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val count = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.board.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MainActivity, count)
            adapter = BoardAdapter(count.toDouble()){
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            }
        }

    }
}