package com.example.developanything.data.dataSource

import com.example.developanything.data.model.RequestData
import com.example.developanything.data.model.ResponseData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface KakaoAPI {
    @POST("v2/inference/karlo/t2i")
    @Headers("Content-Type: application/json")
    suspend fun generateImage(
        @Header("Authorization") token: String,
        @Body requestData: RequestData,

        ): Response<ResponseData>

}