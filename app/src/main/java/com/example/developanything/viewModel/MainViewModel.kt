package com.example.developanything.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.developanything.BuildConfig
import com.example.developanything.data.dataSource.KakaoAPI
import com.example.developanything.data.model.RequestData
import com.example.developanything.data.model.ResponseData
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel: ViewModel() {
    private val kakaoAPI = Retrofit.Builder()
        .baseUrl("https://api.kakaobrain.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(KakaoAPI::class.java)

    private val restAPIKey = BuildConfig.REST_API_KEY
    val response = MutableLiveData<ResponseData>()

    fun generateImage(prompt: String) {
        Log.d("MainViewModel", "Generating image with prompt: $prompt")
        viewModelScope.launch() {
            val requestData = RequestData(prompt = prompt)
            val result = kakaoAPI.generateImage(restAPIKey, requestData)
            response.value = result.body()
        }
    }
}