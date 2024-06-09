package com.example.developanything.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.developanything.data.model.KtorRequestData
import com.example.developanything.data.model.KtorResponseData
import com.example.developanything.ktor.KtorRepository
import io.ktor.util.Identity.decode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.serializer

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
                _responseData.emit(response)
                Log.i(TAG, "requestKtor - success: $response")
            } catch(th: Throwable) {
                Log.e(TAG, "Error:Code: ${server.getErrorStatus(th)}")
            }
        }
    }
}