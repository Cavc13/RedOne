package com.kostas.redone.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.kostas.redone.databinding.ItemDollarInfoBinding
import com.kostas.redone.domain.model.Dollar

class DollarInfoAdapter : ListAdapter<Dollar, DollarViewHolder> (DollarDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DollarViewHolder {
        return DollarViewHolder(ItemDollarInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: DollarViewHolder, position: Int) {
        val dollar = getItem(position)
        with(holder.binding) {
            with(dollar) {
                tvPrice.text = "$value руб."
                tvDate.text = "Дата $date"
            }
        }
    }
}