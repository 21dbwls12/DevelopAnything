package com.example.developanything.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.developanything.BuildConfig
import com.example.developanything.retrofit.KakaoAPI
import com.example.developanything.model.RetrofitRequestData
import com.example.developanything.model.RetrofitResponseData
import com.example.developanything.retrofit.RetrofitFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitViewModel : ViewModel() {
    companion object {
        private const val TAG: String = "RetrofitViewModel"
    }

    private val restAPIKey = BuildConfig.REST_API_KEY

    private val retrofitCallAPI: KakaoAPI = RetrofitFactory.create()
    val response = MutableLiveData<RetrofitResponseData>()

    fun generateImageCall(prompt: String, requestData: MutableState<RetrofitRequestData>) {
        Log.d(TAG, "Generating image with prompt: $prompt")

        val call: Call<RetrofitResponseData> =
            retrofitCallAPI.getGenerateImage(restAPIKey, requestData.value)
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
    }
}