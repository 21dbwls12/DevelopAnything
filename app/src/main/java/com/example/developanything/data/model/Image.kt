package com.example.developanything.data.model

import kotlinx.serialization.Serializable

data class Image(
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
