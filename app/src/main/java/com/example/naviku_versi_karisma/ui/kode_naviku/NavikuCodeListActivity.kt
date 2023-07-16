package com.example.naviku_versi_karisma.ui.kode_naviku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.naviku_versi_karisma.R
import com.example.naviku_versi_karisma.databinding.ActivityNavikuCodeListBinding
import com.example.naviku_versi_karisma.ui.category.ruangan.RuanganCategoryActivity
import com.example.naviku_versi_karisma.ui.kodeku.Kodeku

class NavikuCodeListActivity : AppCompatActivity() {

    private var _activityNavikuCodeListBinding: ActivityNavikuCodeListBinding? = null
    private val binding get() = _activityNavikuCodeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityNavikuCodeListBinding = ActivityNavikuCodeListBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.ibRuangan?.setOnClickListener {
            val intent = Intent(this@NavikuCodeListActivity, RuanganCategoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding?.btnBackNavikuCode?.setOnClickListener {
            val back = Intent(this@NavikuCodeListActivity, Kodeku::class.java)
            startActivity(back)
            finish()
        }
    }
}