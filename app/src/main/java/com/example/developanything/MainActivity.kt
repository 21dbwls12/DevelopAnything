package com.example.developanything

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.developanything.screen.CertificationScreen
import com.example.developanything.ui.theme.DarkColors
import com.example.developanything.ui.theme.DevelopAnythingTheme
import com.example.developanything.ui.theme.LightColors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val colors = if (isSystemInDarkTheme()) DarkColors else LightColors
            DevelopAnythingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colors.background
                ) {
                    CertificationScreen()
                }
            }
        }
    }
}