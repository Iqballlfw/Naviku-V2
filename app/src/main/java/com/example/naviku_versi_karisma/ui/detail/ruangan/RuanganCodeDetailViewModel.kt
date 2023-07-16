package com.example.naviku_versi_karisma.ui.detail.ruangan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.naviku_versi_karisma.data.remote.ApiConfig
import com.example.naviku_versi_karisma.data.response.DetailDataItem
import com.example.naviku_versi_karisma.data.response.RuanganDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RuanganCodeDetailViewModel : ViewModel() {

    private val _ruanganCodeDetailResponse = MutableLiveData<RuanganDetailResponse>()
    val ruanganCodeDetailResponse: LiveData<RuanganDetailResponse> = _ruanganCodeDetailResponse

    private val _ruangaDetailCode = MutableLiveData<DetailDataItem>()
    val ruangaDetailCode: LiveData<DetailDataItem> = _ruangaDetailCode

    companion object {
        private const val TAG = "RuanganCodeDetailViewModel"
    }

    fun setCodeDetail(id: String) {
        val client = ApiConfig.getApiService().getCode(id)
        client.enqueue(object : Callback<RuanganDetailResponse> {
            override fun onResponse(call: Call<RuanganDetailResponse>, response: Response<RuanganDetailResponse>) {
                if (response.isSuccessful) {
                    _ruanganCodeDetailResponse.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RuanganDetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}