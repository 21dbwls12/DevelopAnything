package com.example.developanything

import androidx.compose.runtime.Composable

@Composable
fun FirstScreen(number: String) {
    CenterColumn {
        MiddleText(text = "${number}번 화면입니다!")
    }
}