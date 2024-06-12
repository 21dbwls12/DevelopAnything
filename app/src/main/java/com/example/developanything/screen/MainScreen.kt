package com.example.developanything.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.developanything.model.KtorRequestData
import com.example.developanything.model.RetrofitRequestData
import com.example.developanything.viewModel.KtorViewModel
import com.example.developanything.viewModel.RetrofitViewModel

@Composable
fun MainScreen(retrofitViewModel: RetrofitViewModel, ktorViewModel: KtorViewModel) {
    var clickRetrofit by remember { mutableStateOf(true) }
    var clickKtor by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var prompt by remember { mutableStateOf("") }
    val retrofitResponse by retrofitViewModel.response.observeAsState()
    var painter by remember { mutableStateOf<String?>(null) }
    val retrofitData = remember { mutableStateOf(RetrofitRequestData(prompt = prompt)) }
    val ktorData by ktorViewModel.responseData.collectAsState()

    LaunchedEffect(key1 = retrofitResponse?.images) {
        painter = retrofitResponse?.images?.getOrNull(0)?.image
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            ChoiceButton(weight = 0.5f, text = "retrofit2", clickButton = clickRetrofit) {
                clickRetrofit = true
                clickKtor = false
            }
            ChoiceButton(weight = 1f, text = "ktor", clickButton = clickKtor) {
                clickRetrofit = false
                clickKtor = true
            }
        }
        if (clickRetrofit) {
            PromptLine(
                value = prompt,
                onValueChange = {
                    prompt = it
                    retrofitData.value = RetrofitRequestData(prompt = it)
                }
            ) {
                val currentPrompt = prompt
                retrofitViewModel.generateImage(currentPrompt, retrofitData)
                keyboardController?.hide()
                prompt = ""
            }

            painter?.let {
                ResponseImage(it)
            }
        } else {
            PromptLine(value = prompt, onValueChange = { prompt = it }) {
                val currentPrompt = prompt
                ktorViewModel.requestKtor(KtorRequestData(prompt = currentPrompt))
                keyboardController?.hide()
                prompt = ""
            }
            if (ktorData != null) {
                ResponseImage(painter = ktorData!!.images[0].image)
            }
        }
    }
}

@Composable
fun ChoiceButton(weight: Float, text: String, clickButton: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = if (!clickButton) ButtonDefaults.buttonColors(Color.Transparent) else ButtonDefaults.buttonColors(),
        modifier = Modifier.fillMaxWidth(weight)
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PromptLine(value: String, onValueChange: (String) -> Unit, onClick: () -> Unit) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            Modifier
                .fillMaxWidth(0.85f)
        )
        Button(
            onClick = onClick,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .fillMaxWidth(1f)
        ) {
            Text(text = "생성")
        }
    }
}

@Composable
fun ResponseImage(painter: String) {
    val context = LocalContext.current
    val imageLoader = ImageLoader(context)

    AsyncImage(
        model = painter,
        contentDescription = null,
        imageLoader = imageLoader,
    )
}