package com.example.naviku_versi_karisma.ui.category.ruangan

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

class RuanganCategoryViewModel : ViewModel() {

    private val _codeResponse = MutableLiveData<CodeResponse>()
    val codeResponse: LiveData<CodeResponse> = _codeResponse

    private val _dataItem = MutableLiveData<List<DataItem>>()
    val dataItem: LiveData<List<DataItem>> = _dataItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "RuanganCategoryViewModel"
    }

    init {
        showCodeList()
    }

    private fun showCodeList() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllCodes()
        client.enqueue(object : Callback<CodeResponse> {
            override fun onResponse(call: Call<CodeResponse>, response: Response<CodeResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _codeResponse.value = response.body()
                    _dataItem.value = response.body()?.data
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CodeResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}