package com.aait.nqueensgameapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aait.nqueensgameapp.databinding.ItemBoardBinding
import kotlin.math.ceil
import kotlin.math.roundToInt

class BoardAdapter(
    val boardCount: Double,
    private val boardList: ArrayList<BoardModel>,
    val onItemClicked: (Boolean) -> Unit,
    val goalAchieved:()-> Unit
) : RecyclerView.Adapter<BoardAdapter.MyViewHolder>() {

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
        val item = boardList[position]
        holder.binding.boardItem.setOnClickListener {

        }
        // because position starts from 0
        holder.bind(position + 1, item)
    }

    override fun getItemCount() = (boardCount * boardCount).toInt()

    private fun getHasQueenItemsCount(): Int {
        return boardList.filter { it.hasQueen }.size
    }

    private fun getHasQueenItems(): List<BoardModel> {
        return boardList.filter { it.hasQueen }
    }

    inner class MyViewHolder(val binding: ItemBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, boardModel: BoardModel) {
            // Y point
            val positionInColumn = ceil(position / boardCount).toInt()
            // X Point
            val positionInRow =
                (((position / boardCount) - (positionInColumn - 1)) * boardCount).roundToInt()
            boardModel.position = Point(positionInRow, positionInColumn)

            if (positionInColumn.isOdd() && positionInRow.isOdd() || positionInColumn.isEven() && positionInRow.isEven()) {
                binding.boardItem.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.purple_500
                    )
                )
            }
            binding.queen.isVisible = boardModel.hasQueen

            if (boardModel.isError) {
                binding.boardItem.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.red
                    )
                )
            }

            binding.boardItem.setOnClickListener {
                if (boardModel.hasQueen) {
                    boardModel.hasQueen = false
                    onItemClicked(false)
                } else {
                    if (getHasQueenItemsCount() < 8) {
                        boardModel.hasQueen = true
                        onItemClicked(true)
                        setZonePoints(boardModel)
                    }
                }
                notifyDataSetChanged()
                if (checkGoal()){
                    goalAchieved()
                }
            }
        }

        private fun Int.isOdd(): Boolean {
            return this % 2 != 0
        }

        private fun Int.isEven(): Boolean {
            return this % 2 == 0
        }

        private fun setZonePoints(boardModel: BoardModel) {
            setColumnPoints(boardModel)
            setRowPoints(boardModel)
            setTopRightPoints(boardModel)
            setBottomRightPoints(boardModel)
            setTopLeftPoints(boardModel)
            setBottomLeftPoints(boardModel)
        }

        private fun setColumnPoints(boardModel: BoardModel) {
            val point = boardModel.position
            val x = point.x
            for (i in 1..8) {
                boardModel.zonePoints.add(Point(x, i))
            }
//    println("eslam_points Column ${boardModel.zonePoints}")
        }

        private fun setRowPoints(boardModel: BoardModel) {
            val point = boardModel.position
            val y = point.y
            for (i in 1..8) {
                boardModel.zonePoints.add(Point(i, y))
            }
//    println("eslam_points Row ${boardModel.zonePoints}")
        }

        private fun setTopRightPoints(boardModel: BoardModel) {
            val point = boardModel.position
            val x = point.x
            var y = point.y
            if (x != 8 && y != 1) {
                for (i in (x + 1)..8) {
                    if (y > 1) {
                        boardModel.zonePoints.add(Point(i, --y))
                    }
                }
            }
//    println("eslam_points Top Right ${boardModel.zonePoints}")
        }

        private fun setBottomRightPoints(boardModel: BoardModel) {
            val point = boardModel.position
            val x = point.x
            var y = point.y
            if (x != 8 && y != 8) {
                for (i in (x + 1)..8) {
                    if (y < 8) {
                        boardModel.zonePoints.add(Point(i, ++y))
                    }
                }
            }
//    println("eslam_points Bottom Right ${boardModel.zonePoints}")
        }

        private fun setTopLeftPoints(boardModel: BoardModel) {
            val point = boardModel.position
            val x = point.x
            var y = point.y
            if (x != 1 && y != 1) {
                for (i in (x - 1) downTo 1) {
                    if (y > 1) {
                        boardModel.zonePoints.add(Point(i, --y))
                    }
                }
            }
//    println("eslam_points Top Left ${boardModel.zonePoints}")
        }

        private fun setBottomLeftPoints(boardModel: BoardModel) {
            val point = boardModel.position
            val x = point.x
            var y = point.y
            if (x != 1 && y != 1) {
                for (i in (x - 1) downTo 1) {
                    if (y < 8) {
                        boardModel.zonePoints.add(Point(i, ++y))
                    }
                }
            }
        }

        private fun checkGoal(): Boolean{
            var returnValue = true
            setAllItemsToNoErrors()
            getHasQueenItems().forEach { item1 ->
                getHasQueenItems().forEach { item2 ->
                    if (item1 != item2){
                        item2.zonePoints.forEach { item3 ->
                            if (item1.position == item3){
                                item1.isError = true
                                item2.isError = true
                                returnValue = false
                                println("log_test ${item1.position} ${item2.position}")
                                notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
            return returnValue && getHasQueenItemsCount() == 8
        }

        private fun setAllItemsToNoErrors(){
            boardList.forEach { it.isError = false }
        }
    }
}