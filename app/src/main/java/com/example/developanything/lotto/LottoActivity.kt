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
import kotlin.math.abs
import kotlin.random.Random

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
    // 로또 번호가 바뀌는 애니메이션
    val isNumberMoving = remember {
        mutableStateListOf(false, false, false, false, false, false, false)
    }
    val currentNumbers = remember { mutableStateListOf(7, 7, 7, 7, 7, 7, 7) }
    val generatedNumber = remember { mutableStateListOf(7, 7, 7, 7, 7, 7, 7) }
    val delayTimes = remember {
        mutableStateListOf(
            Random.nextInt(100, 200),
            Random.nextInt(100, 200),
            Random.nextInt(100, 200),
            Random.nextInt(100, 200),
            Random.nextInt(100, 200),
            Random.nextInt(100, 200),
            Random.nextInt(100, 200),
        )
    }
    var stopIndex by remember { mutableIntStateOf(0) }
    // onClick 안에는 LaunchedEffect를 사용할 수 없으므로, 코루틴 스코프 사용
//    val coroutineScope = rememberCoroutineScope()
    for (i in currentNumbers.indices) {
        LaunchedEffect(key1 = isNumberMoving[i]) {
            while (isNumberMoving[i]) {
                currentNumbers[i] = (currentNumbers[i] % 45) + 1
                if (i == stopIndex && currentNumbers[i] == generatedNumber[i]) {
                    isNumberMoving[i] = false
                    stopIndex++
                } else if (i == stopIndex && abs(currentNumbers[i] - generatedNumber[i]) < 5) {
                    delayTimes[i] += 100
                }
                delay(delayTimes[i].toLong())
            }
        }
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
//                    numberList.forEachIndexed { index, number ->
//                        NumberBall(number, isNumberMoving[index])
//                    }
                    currentNumbers.dropLast(1).forEachIndexed { index, number ->
                        NumberBall(number, isNumberMoving[index])
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
//                    NumberBall(bonus, isNumberMoving.last())
                    NumberBall(currentNumbers.last(), isNumberMoving.last())
                }
            }
            // "로또 번호 생성" 버튼 하단에 고정하기 위함. 현재는 애니메이션 사용중으로 주석 처리
//            Spacer(modifier = Modifier.weight(0.1f))
            TextButton(
                onClick = {
                    isNumberMoving.fill(true)
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

                    for (i in generatedNumber.indices) {
                        generatedNumber[i] = if (i < numberList.size) numberList[i] else bonus
                    }
                    // 코루틴 스코프를 이용해 일정 시간이 지난 후에 번호가 멈추게 하는 방법. 현재는 번호가 시간에 따라가 아니라 연속적으로 바뀌다가 생성된 번호와 같을 때 멈추게 하는 방법으로 변경
//                    coroutineScope.launch {
//                        delay(1000)
//                        for (i in isNumberMoving.indices) {
//                            delay(500)
//                            isNumberMoving[i] = false
//                            currentNumbers[i] = if (i < numberList.size) numberList[i] else bonus
//                        }
//                    }
                },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.offset(y = buttonOffsetY.value),
                enabled = isButtonBounced && !isNumberMoving.any { it },
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
private fun NumberBall(number: Int, isMoving: Boolean) {
    val targetY = animateDpAsState(
        targetValue = if (isMoving) 300.dp else 0.dp,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = ""
    )

    Surface(
        shape = CircleShape,
        color = getBallColor(number),
        modifier = Modifier
            .size(45.dp)
            .padding(5.dp)
//            .offset(y = targetY.value)
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