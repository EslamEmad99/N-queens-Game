package com.aait.nqueensgameapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aait.nqueensgameapp.databinding.ItemBoardBinding
import com.aait.nqueensgameapp.databinding.ItemQueenBinding
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

class QueensAdapter() : RecyclerView.Adapter<QueensAdapter.MyViewHolder>() {

    private var count = 8

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemQueenBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_queen,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    fun addItem() {
        if (count < 8) {
            count++
            println("eslam_count ++++ $count")
            notifyDataSetChanged()
        }
    }

    fun removeItem() {
        if (count > 0){
            count --
            println("eslam_count ---- $count")
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.root.setOnClickListener {
            count--
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = count

    inner class MyViewHolder(val binding: ItemQueenBinding) : RecyclerView.ViewHolder(binding.root)
}