package com.example.developanything.model

import kotlinx.serialization.Serializable

data class RetrofitImage(
    val id: String,
    val seed: Int,
    val image: String
)

@Serializable
data class KtorImage(
    val id: String,
    val seed: Int,
    val image: String
)
