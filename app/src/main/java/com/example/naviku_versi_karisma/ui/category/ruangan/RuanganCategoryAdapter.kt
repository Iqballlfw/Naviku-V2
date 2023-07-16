package com.example.naviku_versi_karisma.ui.category.ruangan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.naviku_versi_karisma.data.response.RuanganResponse
import com.example.naviku_versi_karisma.databinding.ItemRowRuanganCodeBinding

class RuanganCategoryAdapter(private val listRuangan: ArrayList<RuanganResponse>) : RecyclerView.Adapter<RuanganCategoryAdapter.RuanganViewHolder>() {

    inner class RuanganViewHolder(private val binding: ItemRowRuanganCodeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ruangan: RuanganResponse) {
            with(binding) {
                tvItemRuangan.text = ruangan.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RuanganViewHolder {
        val binding = ItemRowRuanganCodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RuanganViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listRuangan.size
    }

    override fun onBindViewHolder(holder: RuanganViewHolder, position: Int) {
        holder.bind(listRuangan[position])
    }

}
