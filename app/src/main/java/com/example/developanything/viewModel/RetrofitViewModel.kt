package com.example.developanything.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.developanything.BuildConfig
import com.example.developanything.data.dataSource.KakaoAPI
import com.example.developanything.data.model.RetrofitRequestData
import com.example.developanything.data.model.RetrofitResponseData
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitViewModel : ViewModel() {
    private val kakaoAPI = Retrofit.Builder()
        .baseUrl("https://api.kakaobrain.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", "KakaoAK ${BuildConfig.REST_API_KEY}")
                    .header("Content-Type", "application/json")
                    .method(original.method, original.body)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build())
        .build()
        .create(KakaoAPI::class.java)

    private val restAPIKey = BuildConfig.REST_API_KEY
    val response = MutableLiveData<RetrofitResponseData>()

    fun generateImage(prompt: String) {
        Log.d("RetrofitViewModel", "Generating image with prompt: $prompt")
        viewModelScope.launch() {
            val retrofitRequestData = RetrofitRequestData(prompt = prompt)
            val result = kakaoAPI.generateImage(restAPIKey, retrofitRequestData)
            response.postValue(result.body())
        }
    }
}