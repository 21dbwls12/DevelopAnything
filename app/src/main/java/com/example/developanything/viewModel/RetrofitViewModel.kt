package com.example.developanything.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.developanything.BuildConfig
import com.example.developanything.data.dataSource.KakaoAPI
import com.example.developanything.data.model.RetrofitRequestData
import com.example.developanything.data.model.RetrofitResponseData
import io.ktor.util.Identity.decode
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitViewModel : ViewModel() {
    companion object {
        private const val TAG: String = "RetrofitViewModel"
    }
    private val restAPIKey = BuildConfig.REST_API_KEY
    private val kakaoAPI = Retrofit.Builder()
        .baseUrl("https://api.kakaobrain.com/")
        .addConverterFactory(GsonConverterFactory.create())
        // header(400, -8) 에러를 해결하기 위함
        .client(
            OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", "KakaoAK ${restAPIKey}")
                    .header("Content-Type", "application/json")
                    .method(original.method, original.body)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build())
        .build()

    private val retrofitAPI: KakaoAPI = kakaoAPI.create(KakaoAPI::class.java)
    val response = MutableLiveData<RetrofitResponseData>()

    fun generateImage(prompt: String, requestData: MutableState<RetrofitRequestData>) {
        Log.d(TAG, "Generating image with prompt: $prompt")

        val call: Call<RetrofitResponseData> =
            retrofitAPI.getGenerateImage(restAPIKey, requestData.value)
        call.enqueue(
            object : Callback<RetrofitResponseData> {
                override fun onResponse(
                    call: Call<RetrofitResponseData>,
                    response: Response<RetrofitResponseData>,
                ) {
                    Log.d(TAG, "onResponse success")
                    Log.d(TAG, "response: $response")
                    Log.d(TAG, "response body: ${response.body()}")
                    Log.d(TAG, "retrofit request: ${requestData.value}")
                    Log.d(TAG, "call: ${call.request()}")

                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        this@RetrofitViewModel.response.postValue(responseBody)
                        Log.d(TAG, "response is successful")
                        Log.d(TAG, "responseBody: $responseBody")
                    } else {
                        Log.e(TAG, "Server responded with status code: ${response.code()}")
                        Log.e(TAG, "Error body: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<RetrofitResponseData>, t: Throwable) {
                    Log.d(TAG, "onResponse fail")
                }
            }
        )

//        viewModelScope.launch() {
//            val retrofitRequestData = RetrofitRequestData(prompt = prompt)
//            val result = retrofitAPI.getGenerateImage(restAPIKey, retrofitRequestData)
//            response.postValue(result.decode())
//        }
    }
}