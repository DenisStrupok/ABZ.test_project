package com.abztest.features.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abztest.databinding.RvItemUserBinding
import com.abztest.domain.models.UserModel
import com.squareup.picasso.Picasso

class UsersAdapter(
    private val isLastItem: ((Boolean) -> Unit)? = null
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private var _items = mutableListOf<UserModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListUsers(listUsers: List<UserModel>) {
        if (_items.isEmpty()) {
            _items = listUsers.toMutableList()
        } else {
            _items.addAll(listUsers)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position.plus(1) == _items.size) {
            isLastItem?.invoke(true)
        } else {
            isLastItem?.invoke(false)
        }
        holder.bindItem(item = _items[position])
    }

    class ViewHolder(private val binding: RvItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: UserModel) {
            binding.itemUserNameTv.text = item.name
            binding.itemUserPositionTv.text = item.position
            binding.itemUserEmailTv.text = item.email
            binding.itemUserPhoneTv.text = item.phone
            Picasso.get().load(item.photo).into(binding.itemUserImgV)
        }
    }
}