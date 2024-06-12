package com.example.developanything.model

import kotlinx.serialization.Serializable

data class RetrofitResponseData(
    val id: String,
    val model_version: String,
    val images: List<RetrofitImage>
)

@Serializable
data class KtorResponseData(
    val id: String,
    val model_version: String,
    val images: List<KtorImage>
)