package com.example.developanything.data.model

data class ResponseData(
    val id: String,
    val model_version: String,
    val images: List<Image>
)
