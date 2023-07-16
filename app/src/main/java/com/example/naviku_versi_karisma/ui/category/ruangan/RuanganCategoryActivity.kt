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

        // Inisialisasi ruanganCategoryViewModel
        ruanganCategoryViewModel = ViewModelProvider(this)[RuanganCategoryViewModel::class.java]

        binding.rvRuanganCodes.layoutManager = LinearLayoutManager(this)
        binding.rvRuanganCodes.setHasFixedSize(true)

        ruanganCategoryViewModel.dataItem.observe(this) { ruanganCodeList ->
            setRuanganCodeList(ruanganCodeList)
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

    private fun setRuanganCodeList(ruanganCode: List<DataItem>) {
        val listCode = ArrayList<String>()
        for (code in ruanganCode) {
            listCode.add(code.name)
        }
        val adapter = RuanganCategoryAdapter(listCode)
        binding.rvRuanganCodes.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}
