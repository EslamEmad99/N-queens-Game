package com.aait.nqueensgameapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.aait.nqueensgameapp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val queensAdapter = QueensAdapter()
    private val count = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.queens.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MainActivity, 4)
            adapter = queensAdapter
        }

        val boards = ArrayList<BoardModel>()
        for (i in 1..count * count) {
            boards.add(BoardModel())
        }
        binding.board.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MainActivity, count)
            adapter = BoardAdapter(count.toDouble(), boards, {
                if (it) {
                    queensAdapter.removeItem()
                } else {
                    queensAdapter.addItem()
                }
            }, {
                val dialogBuilder = MaterialAlertDialogBuilder(this@MainActivity)
                dialogBuilder.setTitle(getString(R.string.congrats))
                dialogBuilder.setMessage(getString(R.string.you_win))
                dialogBuilder.setPositiveButton(getString(R.string.ok)){d, w ->

                }
                dialogBuilder.show()
            })
        }
    }
}