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
            binding.itemPositionChBox.isChecked = item.isSelected
            binding.itemPositionChBox.setOnClickListener {
                updateSelection(position)
                actionSelectedPosition?.invoke(item)
            }
            binding.itemPositionNameTV.text = item.name
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateSelection(newPosition: Int) {
        _items = _items.mapIndexed { index, item ->
            item.copy(isSelected = index == newPosition)
        }
        notifyDataSetChanged()
    }
}