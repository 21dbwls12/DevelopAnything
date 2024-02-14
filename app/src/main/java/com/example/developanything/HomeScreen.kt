package com.example.developanything

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    CenterColumn {
        val screenName = listOf("first", "second")
        // 클래스 사용하지 않고 정의
        screenName.forEachIndexed { i, name ->
            MoveButton(onClick = { navController.navigate("$name/${i + 1}") }, number = i + 1)
        }
        // 클래스 사용해서 정의
        MoveButton(
            onClick = { navController.navigate(NavRoutes.Third(3).createRoute()) },
            number = 3
        )
    }
}

@Composable
fun MoveButton(onClick: () -> Unit, number: Int) {
    Button(
        onClick,
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(Color.Black)
    ) {
        Text(text = "${number}번 화면으로 이동", fontSize = 20.sp, color = Color.White)
    }
}