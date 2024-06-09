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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitViewModel: ViewModel() {
    private val kakaoAPI = Retrofit.Builder()
        .baseUrl("https://api.kakaobrain.com/")
        .addConverterFactory(GsonConverterFactory.create())
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
            Log.d("RetrofitViewModel", "Response from server: $response")
            Log.d("RetrofitViewModel", "Response from server: ${response.value}")
        }
    }
}