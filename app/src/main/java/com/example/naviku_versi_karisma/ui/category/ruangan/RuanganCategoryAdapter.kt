package com.example.naviku_versi_karisma.ui.category.ruangan

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.naviku_versi_karisma.databinding.ItemRowRuanganCodeBinding
import com.example.naviku_versi_karisma.ui.detail.ruangan.RuanganCodeDetailActivity
import com.example.naviku_versi_karisma.ui.detail.ruangan.RuanganCodeDetailActivity.Companion.EXTRA_ID


class RuanganCategoryAdapter(private val listRuangan: List<String>) :
    RecyclerView.Adapter<RuanganCategoryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemRowRuanganCodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(code: String) {
                binding.apply {
                    tvItemRuangan.text = code
                }

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, RuanganCodeDetailActivity::class.java)
                    intent.putExtra(EXTRA_ID, code)
                    itemView.context.startActivity(intent)
                }
            }
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
        holder.bind(listRuangan[position])
    }
}