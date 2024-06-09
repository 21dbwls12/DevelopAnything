package com.example.developanything.data.model

import retrofit2.http.Url

data class Image(
    val id: String,
    val seed: Int,
    val image: String
)
