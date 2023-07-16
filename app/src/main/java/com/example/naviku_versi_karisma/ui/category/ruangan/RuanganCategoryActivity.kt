package com.example.naviku_versi_karisma.ui.category.ruangan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naviku_versi_karisma.data.remote.ApiConfig
import com.example.naviku_versi_karisma.data.response.DataItem
import com.example.naviku_versi_karisma.databinding.ActivityRuanganCategoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

class RuanganCategoryActivity : AppCompatActivity() {

    private lateinit var activityRuanganCategoryBinding: ActivityRuanganCategoryBinding
    private lateinit var adapter: RuanganCategoryAdapter

    private val list = ArrayList<DataItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRuanganCategoryBinding = ActivityRuanganCategoryBinding.inflate(layoutInflater)
        setContentView(activityRuanganCategoryBinding.root)
    }
}
