package com.example.naviku_versi_karisma.ui.category.ruangan

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

    private val _dataItem = MutableLiveData<DataItem>()
    val dataItem: LiveData<DataItem> = _dataItem

    companion object {
        private const val TAG = "RuanganCategoryViewModel"
    }

    private fun showCodeList() {
        val client = ApiConfig.getApiService().getAllCodes()
    }
}