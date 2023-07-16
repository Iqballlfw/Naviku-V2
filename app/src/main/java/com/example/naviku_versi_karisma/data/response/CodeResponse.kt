package com.example.naviku_versi_karisma.data.response

import com.google.gson.annotations.SerializedName

 class CodeResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String
)

 class DataItem(

	@field:SerializedName("pdfFile")
	val pdfFile: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("qrImage")
	val qrImage: String,

	@field:SerializedName("id")
	val id: String
)
