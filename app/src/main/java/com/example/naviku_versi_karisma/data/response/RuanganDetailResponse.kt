package com.example.naviku_versi_karisma.data.response

import com.google.gson.annotations.SerializedName

data class RuanganDetailResponse(

	@field:SerializedName("data")
	val data: List<DetailDataItem>,

	@field:SerializedName("message")
	val message: String
)

data class DetailDataItem(

	@field:SerializedName("pdfFile")
	val pdfFile: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("qrImage")
	val qrImage: String,

	@field:SerializedName("id")
	val id: String
)
