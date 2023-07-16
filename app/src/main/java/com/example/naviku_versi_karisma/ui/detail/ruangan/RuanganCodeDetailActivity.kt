package com.example.naviku_versi_karisma.ui.detail.ruangan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.naviku_versi_karisma.databinding.ActivityRuanganCodeDetailBinding

class RuanganCodeDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityRuanganCodeDetailBinding
    private val viewModel by viewModels<RuanganCodeDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRuanganCodeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(EXTRA_ID)
        viewModel.setCodeDetail(id)

        viewModel.ruanganDetailCode.observe(this) { detailCode ->
            binding.apply {
                tvCodeDesc.text = detailCode.name

                Glide.with(this@RuanganCodeDetailActivity)
                    .load(detailCode.qrImage)
                    .into(ivCodeImg)
            }
        }
    }
}
