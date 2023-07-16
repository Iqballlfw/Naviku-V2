package com.example.naviku_versi_karisma.ui.category.ruangan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naviku_versi_karisma.data.response.DataItem
import com.example.naviku_versi_karisma.databinding.ActivityRuanganCategoryBinding
import com.example.naviku_versi_karisma.ui.kodeku.Kodeku

class RuanganCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRuanganCategoryBinding
    private lateinit var ruanganCategoryViewModel: RuanganCategoryViewModel

    companion object {
        private const val TAG = "RuanganCategoryActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRuanganCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ruanganCategoryViewModel = ViewModelProvider(this)[RuanganCategoryViewModel::class.java]

        showRecyclerViewList()
        ruanganCategoryViewModel.dataItem.observe(this) { listRuanganCode ->
            binding.rvRuanganCodes.adapter = RuanganCategoryAdapter(listRuanganCode)
        }

        ruanganCategoryViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.btnBackNavikuCode.setOnClickListener {
            val back = Intent(this@RuanganCategoryActivity, Kodeku::class.java)
            startActivity(back)
            finish()
        }

        binding.abRuanganCodeList.ivBackRuanganCode.setOnClickListener {
            val back = Intent(this@RuanganCategoryActivity, Kodeku::class.java)
            startActivity(back)
            finish()
        }
    }

    private fun showRecyclerViewList() {
        binding.rvRuanganCodes.apply {
            layoutManager = LinearLayoutManager(this@RuanganCategoryActivity)
            setHasFixedSize(true)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}