package com.kostas.redone.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kostas.redone.domain.model.Dollar

object DollarDiffCallback: DiffUtil.ItemCallback<Dollar>() {
    override fun areItemsTheSame(oldItem: Dollar, newItem: Dollar): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Dollar, newItem: Dollar): Boolean {
        return  oldItem == newItem
    }
}