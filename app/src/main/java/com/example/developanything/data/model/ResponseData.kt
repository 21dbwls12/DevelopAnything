package com.example.developanything.data.model

import kotlinx.serialization.Serializable

data class ResponseData(
    val id: String,
    val model_version: String,
    val images: List<Image>
)

@Serializable
data class KtorResponseData(
    val id: String,
    val model_version: String,
    val images: List<KtorImage>
)