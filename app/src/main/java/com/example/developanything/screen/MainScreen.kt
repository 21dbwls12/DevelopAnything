package com.example.developanything.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MainScreen(restAPIKey: String) {
    var clickRetrofit by remember { mutableStateOf(true) }
    var clickKtor by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            ChoiceButton(weight = 0.5f, text = "retrofit2") {
                clickRetrofit = true
                clickKtor = false
            }
            ChoiceButton(weight = 1f, text = "ktor") {
                clickRetrofit = false
                clickKtor = true
            }
        }
        if (clickRetrofit) {
            RetrofitPage("retrofit2")
        } else {
            KtorPage("ktor")
        }
    }
}

@Composable
fun ChoiceButton(weight: Float, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
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
fun RetrofitPage(name: String) {
    Text(
        text = "Hello $name!",
    )
}

@Composable
fun KtorPage(name: String) {
    Text(
        text = "Hello $name!",
    )
}