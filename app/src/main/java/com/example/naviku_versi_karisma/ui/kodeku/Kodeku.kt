package com.example.naviku_versi_karisma.ui.kodeku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.naviku_versi_karisma.R
import com.example.naviku_versi_karisma.databinding.ActivityKodekuBinding
import com.example.naviku_versi_karisma.ui.kode_pribadi.PersonalCodeListActivity
import com.example.naviku_versi_karisma.ui.main.MainActivity

class Kodeku : AppCompatActivity() {

    private lateinit var binding: ActivityKodekuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.KodekuTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityKodekuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHomeKodeku.setOnClickListener {
            val homePage = Intent(this@Kodeku, MainActivity::class.java)
            startActivity(homePage)
            finish()
        }

        binding.ibPersonalCode.setOnClickListener {
            val personalCodeList = Intent(this@Kodeku, PersonalCodeListActivity::class.java)
            startActivity(personalCodeList)
            finish()
        }
    }
}