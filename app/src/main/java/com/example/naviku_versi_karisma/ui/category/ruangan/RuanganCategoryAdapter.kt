package com.example.naviku_versi_karisma.ui.category.ruangan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.naviku_versi_karisma.R
import com.example.naviku_versi_karisma.databinding.ItemRowRuanganCodeBinding


class RuanganCategoryAdapter(private val listRuangan: List<String>) :
    RecyclerView.Adapter<RuanganCategoryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemRowRuanganCodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvItemRuangan = binding.tvItemRuangan
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowRuanganCodeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = listRuangan.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvItemRuangan.text = listRuangan[position]
    }
}
