package com.example.developanything.lotto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.developanything.R
import com.example.developanything.ui.theme.DevelopAnythingTheme

class LottoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevelopAnythingTheme {
                LottoScreen()
            }
        }
    }
}

@Composable
fun LottoScreen() {
    val numberList = remember { mutableStateListOf(7, 7, 7, 7, 7, 7) }
    var bonus by remember { mutableIntStateOf(7) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        color = Color.White,
    ) {
        Image(
            painter = painterResource(id = R.drawable.lottologo),
            contentDescription = "lotto logo"
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f)
            ) {
                NumberBox {
                    numberList.forEach { number ->
                        NumberBall(number)
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "+",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF999999),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.width(5.dp))
                NumberBox {
                    NumberBall(bonus)
                }
            }
            Spacer(modifier = Modifier.weight(0.1f))
            TextButton(
                onClick = {
                    numberList.clear()
                    while (numberList.size < 6) {
                        val random = (1..45).random()
                        if (random !in numberList) {
                            numberList.add(random)
                        }
                    }
                    do {
                        bonus = (1..45).random()
                    } while (bonus in numberList)
                },
                shape = RoundedCornerShape(15.dp),
            ) {
                Text(text = "로또 번호 생성", fontWeight = FontWeight.Bold, fontSize = 25.sp)
            }
        }
    }
}

@Composable
private fun NumberBox(content: @Composable () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0xFFFAFAFA), shape = RoundedCornerShape(10.dp))
            .padding(5.dp)
    ) {
        content()
    }
}

@Composable
private fun NumberBall(number:Int) {
    Surface(
        shape = CircleShape,
        color = Color.Blue,
        modifier = Modifier
            .size(45.dp)
            .padding(5.dp)
    ) {
        Text(
            text = number.toString(),
            fontSize = 22.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize().padding(2.dp)
        )
    }
}