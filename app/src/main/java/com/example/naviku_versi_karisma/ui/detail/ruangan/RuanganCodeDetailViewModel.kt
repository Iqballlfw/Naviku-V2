package com.example.naviku_versi_karisma.ui.detail.ruangan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.naviku_versi_karisma.data.remote.ApiConfig
import com.example.naviku_versi_karisma.data.response.CodeResponse
import com.example.naviku_versi_karisma.data.response.DataItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RuanganCodeDetailViewModel : ViewModel() {

    private val _ruanganCodeDetailResponse = MutableLiveData<CodeResponse>()
    val ruanganCodeDetailResponse: LiveData<CodeResponse> = _ruanganCodeDetailResponse

    private val _ruanganDetailCode = MutableLiveData<DataItem>()
    val ruanganDetailCode: LiveData<DataItem> = _ruanganDetailCode

    companion object {
        private const val TAG = "RuanganCodeDetailViewModel"
    }

    fun setCodeDetail(id: String?) {
        val client = ApiConfig.getApiService().getCode(id)
        client.enqueue(object : Callback<DataItem> {
            override fun onResponse(call: Call<DataItem>, response: Response<DataItem>) {
                if (response.isSuccessful) {
                    _ruanganDetailCode.postValue(response.body())
                    Log.e(TAG, "Berhasil")

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DataItem>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}