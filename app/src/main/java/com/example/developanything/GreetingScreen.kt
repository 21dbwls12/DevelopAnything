package com.example.developanything

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun GreetingScreen(navController: NavController){
    LaunchedEffect(key1 = true){
        delay(2000)
        // 뒤로가기 눌렀을 때 현재 화면으로는 더이상 돌아오지 않게 하기 위해 popUpTo 사용.
        navController.navigate("home") {
            popUpTo("greeting") {
                inclusive = true
            }
        }
    }

    CenterColumn{
        MiddleText(text = "안녕하세요!")
    }
}

@Composable
fun MiddleText(text: String) {
    Text(
        text = text,
        fontSize = 25.sp,
        color = Color.Black
    )
}

@Composable
fun CenterColumn(content: @Composable () ->  Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        content()
    }
}