package com.example.developanything.retrofit

import com.example.developanything.model.RetrofitRequestData
import com.example.developanything.model.RetrofitResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface KakaoAPI {
    @POST("v2/inference/karlo/t2i")
    @Headers("Content-Type: application/json")
    fun getGenerateImage(
        @Header("Authorization") token: String,
        @Body retrofitRequestData: RetrofitRequestData,
        ): Call<RetrofitResponseData>
}