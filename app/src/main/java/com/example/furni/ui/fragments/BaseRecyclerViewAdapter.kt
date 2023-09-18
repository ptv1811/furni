package com.example.furni.ui.fragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseRecyclerViewAdapter<T : Any, VDB : ViewDataBinding>(
    @LayoutRes val layoutId: Int,
    private val clickListener: (Int, VDB) -> Unit = { _, _ ->
    },
    private val onBindViewHolderCallback: (T, VDB) -> Unit = { _, _ ->
    }
) : RecyclerView.Adapter<BaseRecyclerViewAdapter.RecyclerViewHolder<VDB>>() {

    private val items: MutableList<T> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder<VDB> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<VDB>(
            inflater, layoutId, parent, false
        )

        return RecyclerViewHolder(binding).apply {
            binding.root.setOnClickListener {
                clickListener(bindingAdapterPosition, binding)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerViewHolder<VDB>, position: Int) {
        holder.binding.apply {
            val itemAtPos = items[position]
            onBindViewHolderCallback(itemAtPos, this)
        }
    }

    class RecyclerViewHolder<VDB : ViewDataBinding>(val binding: VDB) :
        RecyclerView.ViewHolder(binding.root)
}