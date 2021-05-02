package com.aait.nqueensgameapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aait.nqueensgameapp.databinding.ItemBoardBinding
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

class BoardAdapter(
    val boardCount: Double,
    val onItemClicked: (String) -> Unit
): RecyclerView.Adapter<BoardAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemBoardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_board,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // because position starts from 0
        holder.bind(position +1)
    }

    override fun getItemCount() = (boardCount * boardCount).toInt()

    inner class MyViewHolder(private val binding: ItemBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val positionInColumn = ceil(position / boardCount).toInt()
            val positionInRow = (((position / boardCount) - (positionInColumn -1)) * boardCount).roundToInt()

            if (positionInColumn.isOdd() && positionInRow.isOdd() || positionInColumn.isEven() && positionInRow.isEven()) {
                binding.boardItem.setBackgroundColor(ContextCompat.getColor(
                    binding.root.context,
                    R.color.black
                ))
            }
            binding.boardItem.setOnClickListener {
                onItemClicked("$positionInColumn , $positionInRow")
            }
        }

        private fun Int.isOdd(): Boolean{
            return this % 2 != 0
        }

        private fun Int.isEven(): Boolean{
            return this % 2 == 0
        }
    }
}