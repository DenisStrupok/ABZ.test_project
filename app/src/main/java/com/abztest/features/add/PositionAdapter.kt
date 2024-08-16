package com.abztest.features.add

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abztest.databinding.RvItemPositionBinding
import com.abztest.domain.models.PositionModel

class PositionAdapter(
    private val actionSelectedPosition: ((PositionModel) -> Unit)? = null
) : RecyclerView.Adapter<PositionAdapter.ViewHolder>() {

    private var _items = listOf<PositionModel>()
    private var selectedPosition: Int? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setPositions(list: List<PositionModel>) {
        _items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvItemPositionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(_items[position], position)
    }

    inner class ViewHolder(private val binding: RvItemPositionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PositionModel, position: Int) {
            binding.itemPositionChBox.isChecked = position == selectedPosition
            binding.itemPositionChBox.setOnClickListener {
                if (position != selectedPosition) {
                    val previousPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(previousPosition ?: -1)
                    notifyItemChanged(position)
                    actionSelectedPosition?.invoke(item)
                }

            }
            binding.itemPositionNameTV.text = item.name
        }
    }
}