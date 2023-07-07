package com.example.naviku_versi_karisma.ui.kode_pribadi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naviku_versi_karisma.databinding.ActivityPersonalCodeListBinding
import com.example.naviku_versi_karisma.helper.ViewModelFactory
import com.example.naviku_versi_karisma.ui.add.AddCodeActivity
import com.example.naviku_versi_karisma.ui.kodeku.Kodeku

class PersonalCodeListActivity : AppCompatActivity() {

    private var _activityPersonalCodeListBinding: ActivityPersonalCodeListBinding? = null
    private val binding get() = _activityPersonalCodeListBinding

    private lateinit var adapter: CodeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityPersonalCodeListBinding = ActivityPersonalCodeListBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val personalCodeListViewModel = obtainViewModel(this@PersonalCodeListActivity)
        personalCodeListViewModel.getAllCodes().observe(this) { codeList ->
            if (codeList != null) {
                if (codeList.isNotEmpty()) {
                    binding?.ivBlankImg?.visibility = View.INVISIBLE
                    binding?.tvBlankData?.visibility = View.INVISIBLE
                    adapter.setListCodes(codeList)
                } else {
                    binding?.ivBlankImg?.visibility = View.VISIBLE
                    binding?.tvBlankData?.visibility = View.VISIBLE
                }
            }
        }

        adapter = CodeAdapter()

        binding?.rvCodes?.layoutManager = LinearLayoutManager(this)
        binding?.rvCodes?.setHasFixedSize(true)
        binding?.rvCodes?.adapter = adapter

        binding?.btnCreateCodeList?.setOnClickListener {
            val intent = Intent(this@PersonalCodeListActivity, AddCodeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding?.btnBackCodeList?.setOnClickListener {
            val intent = Intent(this@PersonalCodeListActivity, Kodeku::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityPersonalCodeListBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): PersonalCodeListViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[PersonalCodeListViewModel::class.java]
    }
}