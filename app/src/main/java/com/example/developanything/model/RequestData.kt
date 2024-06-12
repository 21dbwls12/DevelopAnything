package com.example.developanything.model

import kotlinx.serialization.Serializable

data class RetrofitRequestData(
    val version: String = "v2.1",
    val prompt: String,
    val width: Int = 1024,
    val height: Int = 512,
    val image_format: String = "png",
)

@Serializable
data class KtorRequestData(
    val version: String = "v2.1",
    val prompt: String,
    val width: Int = 1024,
    val height: Int = 512,
    val image_format: String = "png",
)