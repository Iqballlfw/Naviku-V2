package com.example.naviku_versi_karisma.ui.detail.ruangan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.naviku_versi_karisma.R
import com.example.naviku_versi_karisma.databinding.ActivityRuanganCodeDetailBinding

class RuanganCodeDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityRuanganCodeDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRuanganCodeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}