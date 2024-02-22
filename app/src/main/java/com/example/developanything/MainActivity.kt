package com.example.developanything

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.developanything.ui.theme.DevelopAnythingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevelopAnythingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
private fun Greeting() {
    val first = (0..2).random()
    var currentNumber by remember { mutableIntStateOf(first) }

    // Crossfade 애니메이션 이용해서 이미지 변경
    Crossfade(targetState = currentNumber, label = "") {
        when (it) {
            0 -> ChangeImage(name = R.drawable.poster0)
            1 -> ChangeImage(name = R.drawable.poster1)
            else -> ChangeImage(name = R.drawable.poster2)
        }
    }
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        Button(
            onClick = {
                var temp = (0..2).random()
                while (currentNumber == temp) {
                    temp = (0..2).random()
                }
                currentNumber = temp
            },
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "다른 이미지 보기",
                color = Color(0xFF04BF9D),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}


@Composable
private fun ChangeImage(name: Int) {
    Image(
        painter = painterResource(id = name),
        contentDescription = "poster",
        contentScale = ContentScale.FillHeight
    )
}