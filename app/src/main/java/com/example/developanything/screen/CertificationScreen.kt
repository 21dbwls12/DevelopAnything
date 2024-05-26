package com.example.developanything.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.developanything.Colors
import com.example.developanything.R
import com.example.developanything.ui.theme.DarkTree
import com.example.developanything.ui.theme.LightTree
import com.example.developanything.ui.theme.Sky

@Composable
fun CertificationScreen(colors: Colors, deviceWidth: Float) {
    val clickNaviIcon by remember { mutableStateOf(false) }

    ScaffoldBar(colors = colors) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            HorizontalDivider(thickness = 1.dp, color = Sky)
            // 인증 목록
            LazyRow(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
//                    .padding(10.dp)
            ) {
                items(2) {
                    HabitCard(color = LightTree, deviceWidth = deviceWidth) {

                    }
                    HabitCard(color = DarkTree, deviceWidth = deviceWidth) {

                    }
                }

            }
            HorizontalDivider(thickness = 1.dp, color = Sky)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldBar(colors: Colors, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "오늘의 진행도", color = colors.star) },
                colors = TopAppBarDefaults.topAppBarColors(colors.background),
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = colors.background,
                contentColor = colors.text,
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
            ) {
                Button(
                    onClick = {  },
                    colors = ButtonDefaults.buttonColors(colors.background),
                    shape = RoundedCornerShape(5.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .padding(0.dp)
                        .size(50.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.filled_premium),
                            contentDescription = "현재 인증 진행도",
                            tint = colors.text,
                            modifier = Modifier
                                .size(30.dp)
                                .padding(0.dp)
                                .clip(RoundedCornerShape(5.dp))
                        )
                        Text(text = "인증", fontSize = 12.sp, color = colors.text)
                    }
                }
            }
        }
    ) {
        content(it)
    }
}

@Composable
fun HabitCard(color: Color, deviceWidth: Float, onclick: () -> Unit) {
    Card(
        onClick = onclick,
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(color),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width((deviceWidth - 40).dp)
            .fillMaxHeight(0.999f)
            .padding(vertical = 10.dp, horizontal = 5.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "매일 인증해야하는 습관",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}