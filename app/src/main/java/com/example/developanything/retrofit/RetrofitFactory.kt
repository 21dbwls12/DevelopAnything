package com.example.developanything.retrofit

import com.example.developanything.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    inline fun <reified Service> create(): Service {
        val restAPIKey = BuildConfig.REST_API_KEY
        return Retrofit.Builder()
            .baseUrl("https://api.kakaobrain.com/")
            .addConverterFactory(GsonConverterFactory.create())
            // header(400, -8) 에러를 해결하기 위함
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val requestBuilder = original.newBuilder()
                            .header("Authorization", "KakaoAK $restAPIKey")
                            .header("Content-Type", "application/json")
                            .method(original.method, original.body)
                        val request = requestBuilder.build()
                        chain.proceed(request)
                    }
                    .build())
            .build()
            .create(Service::class.java)
    }
}