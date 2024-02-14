package com.example.developanything

import androidx.compose.runtime.Composable

// 1번 화면
@Composable
fun FirstScreen(number: String) {
    CenterColumn {
        MiddleText(text = "${number}번 화면입니다!")
    }
}

// 2번 화면
@Composable
fun SecondScreen(number: String) {
    CenterColumn {
        MiddleText(text = "${number}번 화면입니다!")
    }
}

// 3번 화면
@Composable
fun ThirdScreen(number: String) {
    CenterColumn {
        MiddleText(text = "${number}번 화면입니다!")
    }
}