package com.example.naviku_versi_karisma.data.response

import com.google.gson.annotations.SerializedName

data class RuanganResponse(
    @SerializedName("name")
    val name: String?,

    @SerializedName("id")
    val id: String
)
