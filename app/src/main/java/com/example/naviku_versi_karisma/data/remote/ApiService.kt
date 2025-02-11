package com.example.naviku_versi_karisma.data.remote

import com.example.naviku_versi_karisma.data.response.CodeResponse
import com.example.naviku_versi_karisma.data.response.DataItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("ruangan/{id}")
    fun getCode(
        @Path("id") id: String?
    ): Call<DataItem>

    @GET("ruangan")
    fun getAllCodes() : Call<CodeResponse>
}