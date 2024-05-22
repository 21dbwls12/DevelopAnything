package com.example.developanything.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.developanything.R
import com.example.developanything.ui.theme.DarkColors
import com.example.developanything.ui.theme.LightColors
import com.example.developanything.ui.theme.tree

@Composable
@Preview(showBackground = true)
fun PreviewScreen() {
    val colors = if (isSystemInDarkTheme()) DarkColors else LightColors
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colors.background
    ) {
        CertificationScreen()
    }
}

@Composable
fun CertificationScreen() {
    val colors = if (isSystemInDarkTheme()) DarkColors else LightColors

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = "오늘의 진행도는", color = colors.star, fontSize = 40.sp)
        // 인증 목록
        Card(
            onClick = {},
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(tree),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                Image(
                    // 녹음
//                    painter = painterResource(id = androidx.constraintlayout.widget.R.drawable.abc_ic_voice_search_api_material),
                    // 이미지
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "인증하면 올라갈 사진",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "매일 인증해야하는 습관",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}