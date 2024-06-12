package com.example.developanything.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.developanything.model.KtorRequestData
import com.example.developanything.model.KtorResponseData
import com.example.developanything.ktor.KtorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Ktor 사용하는 뷰모델
class KtorViewModel: ViewModel() {
    val server = KtorRepository()
    private val _responseData = MutableStateFlow<KtorResponseData?>(null)
    val responseData: StateFlow<KtorResponseData?> = _responseData

    companion object {
        private const val TAG = "KtorViewModel"
    }

    fun requestKtor(requestData: KtorRequestData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = server.getResponseKtor(requestData)
                // 받아온 응답을 MainScreen에서 사용할 수 있도록 업데이트
                _responseData.emit(response)
                Log.i(TAG, "requestKtor - success: $response")
            } catch(th: Throwable) {
                Log.e(TAG, "Error:Code: ${server.getErrorStatus(th)}")
            }
        }
    }
}