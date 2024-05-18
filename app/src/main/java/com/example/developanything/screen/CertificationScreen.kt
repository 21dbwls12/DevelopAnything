package com.example.developanything.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.tooling.preview.Preview
import com.example.developanything.ui.theme.DarkColors
import com.example.developanything.ui.theme.LightColors

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

    Text(text = "오늘의 진행도는", color = colors.star)
}