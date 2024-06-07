package com.example.developanything.data.model

data class RequestData(
    val version: String = "v2.1",
    val prompt: String,
    val width: Int = 1024,
    val height: Int = 512,
    val image_format: String = "png",
)
