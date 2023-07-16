package com.example.naviku_versi_karisma.ui.category.ruangan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naviku_versi_karisma.data.remote.ApiConfig
import com.example.naviku_versi_karisma.databinding.ActivityRuanganCategoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.naviku_versi_karisma.data.response.RuanganResponse

class RuanganCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRuanganCategoryBinding
    private lateinit var adapter: RuanganCategoryAdapter

    private val list = ArrayList<RuanganResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRuanganCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RuanganCategoryAdapter(list)

        binding.rvRuanganCodes.layoutManager = LinearLayoutManager(this)
        binding.rvRuanganCodes.setHasFixedSize(true)

        ApiConfig.getApiService().getAllCodes().enqueue(object : Callback<RuanganResponse> {
            override fun onResponse(
                call: Call<RuanganResponse>,
                response: Response<RuanganResponse>
            ) {
                if (response.isSuccessful) {
                    val ruanganResponse = response.body()
                    if (ruanganResponse != null) {
                        list.add(ruanganResponse) // Tambahkan ruanganResponse ke dalam list
                    }
                } else {
                    // Handle kesalahan respons dari server
                }
            }

            override fun onFailure(call: Call<RuanganResponse>, t: Throwable) {
                Log.e("RuanganCategoryActivity", "API call failed", t)
            }
        })

        binding.rvRuanganCodes.adapter = adapter // Set adapter ke RecyclerView
    }

}
