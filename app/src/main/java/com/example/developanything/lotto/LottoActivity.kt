package com.example.developanything.lotto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.delay

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
    // 앱 시작할 때 로또 로고의 애니메이션(중앙에서 좌측 상단으로 이동)
    var isImageMoved by remember { mutableStateOf(false) }
    val imageSize = animateDpAsState(
        targetValue = if (isImageMoved) 100.dp else 700.dp,
        label = ""
    )
    val imagePadding = animateDpAsState(
        targetValue = if (isImageMoved) 10.dp else 2.dp,
        label = ""
    )
    // 앱 시작할 때 "로또 번호 생성" 버튼의 애니메이션(위에서 아래로 이동, 튕김)
    var isButtonMoved by remember { mutableStateOf(false) }
    val buttonOffsetY = animateDpAsState(
        targetValue = if (isButtonMoved) 300.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )
    var isButtonBounced by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        delay(1000)
        isImageMoved = true
        isButtonMoved = true
        delay(700)
        isButtonBounced = true
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    // 번호를 중앙에 고정하기 위함. 현재는 애니메이션 사용중으로 주석 처리
//                    .fillMaxHeight(0.9f)
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
            // "로또 번호 생성" 버튼 하단에 고정하기 위함. 현재는 애니메이션 사용중으로 주석 처리
//            Spacer(modifier = Modifier.weight(0.1f))
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
                modifier = Modifier.offset(y = buttonOffsetY.value),
                enabled = isButtonBounced,
                colors = ButtonDefaults.textButtonColors(
                    disabledContentColor = Color.LightGray,
                    contentColor = Color(0xFF00BBC9),
                )
            ) {
                Text(
                    text = "로또 번호 생성",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.lottologo),
                contentDescription = "lotto logo",
                modifier = Modifier
                    .size(imageSize.value)
                    .padding(top = imagePadding.value, start = imagePadding.value)
            )
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
private fun NumberBall(number: Int) {
    Surface(
        shape = CircleShape,
        color = getBallColor(number),
        modifier = Modifier
            .size(45.dp)
            .padding(5.dp)
    ) {
        Text(
            text = number.toString(),
            fontSize = 22.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
        )
    }
}

private fun getBallColor(number: Int): Color {
    return when (number) {
        in 1..10 -> Color(0xFFFBC400)
        in 11..20 -> Color(0xFF69C8F2)
        in 21..30 -> Color(0xFFFF7272)
        in 31..40 -> Color(0xFFAAAAAA)
        else -> Color(0xFFB0d841)
    }
}