package com.example.developanything.data.model

import kotlinx.serialization.Serializable

data class RetrofitResponseData(
    val id: String,
    val model_version: String,
    val retrofitImages: List<RetrofitImage>
)

@Serializable
data class KtorResponseData(
    val id: String,
    val model_version: String,
    val images: List<KtorImage>
)