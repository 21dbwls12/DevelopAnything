package com.example.developanything.ktor

import android.util.Log
import com.example.developanything.BuildConfig
import com.example.developanything.data.model.KtorRequestData
import com.example.developanything.data.model.KtorResponseData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.json.Json
import kotlin.jvm.Throws

class KtorRepository {
    companion object {
        private const val TAG = "ktor sever"
    }

    private val client = HttpClient(CIO) {
        expectSuccess = true

//        install(DefaultRequest) {
//            header(HttpHeaders.Authorization, "KakaoAK ${BuildConfig.REST_API_KEY}")
//            contentType(ContentType.Application.Json)
//            accept(ContentType.Application.Json)
//        }

//        defaultRequest {
//            header(HttpHeaders.Authorization, "KakaoAK ${BuildConfig.REST_API_KEY}")
//            contentType(ContentType.Application.Json)
//        }

        install(ContentNegotiation) {
            json(Json {
                // 모델에 없고, json에 있는경우 해당 key 무시
                ignoreUnknownKeys = true
                prettyPrint = true
                // "" 따옴표 잘못된건 무시하고 처리
                isLenient = true
                //null 인 값도 json에 포함 시킨다.
                encodeDefaults = true
            })
        }

        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    Log.d(TAG, message)
                }
            }
            level = LogLevel.HEADERS
            filter { request ->
                request.url.host.contains("ktor.io")
            }
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
    }

    fun getErrorStatus(th: Throwable): Int = when (th) {
        //Http Code: 3xx
        is RedirectResponseException -> {
            (th.response.status.value)
        }
        //Http Code: 4xx
        is ClientRequestException -> {
            (th.response.status.value)
        }
        //Http Code: 5xx
        is ServerResponseException -> {
            (th.response.status.value)
        }
        // Network Error - Internet Error
        is UnresolvedAddressException -> {
            1000
        }
        // Unknown
        else -> 9999
    }

    @OptIn(InternalAPI::class)
    @Throws
    suspend fun getResponseKtor(requestData: KtorRequestData): KtorResponseData? {
        Log.d(TAG, "requestData: $requestData")
        try {
            val response: HttpResponse = client.post("https://api.kakaobrain.com/v2/inference/karlo/t2i") {
                header(HttpHeaders.Authorization, "KakaoAK ${BuildConfig.REST_API_KEY}")
                contentType(ContentType.Application.Json)
                setBody(body = requestData)
            }
            return response.body<KtorResponseData>()
        } catch (e: Exception) {
            Log.e(TAG, "Error occurred", e)
            return null
        }
    }

//    fun responseAPI(prompt: String) {
//        CoroutineScope(Dispatchers.IO).launch {
//            @OptIn(InternalAPI::class)
//            val response: HttpResponse =
//                client.post("https://api.kakaobrain.com/v2/inference/karlo/t2i") {
//                    header(HttpHeaders.Authorization, "KakaoAK ${BuildConfig.REST_API_KEY}")
//                    contentType(ContentType.Application.Json)
//                    body = RequestData(prompt = prompt)
//                }
//        }
//    }
}