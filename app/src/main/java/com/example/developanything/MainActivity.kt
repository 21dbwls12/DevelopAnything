package com.example.developanything

import android.os.Bundle
import android.util.DisplayMetrics
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
            val display = this.applicationContext?.resources?.displayMetrics
            val width = display?.widthPixels
            val context = this
            val deviceWidth = width!! / ((context.resources.displayMetrics.densityDpi.toFloat()) / DisplayMetrics.DENSITY_DEFAULT)

            DevelopAnythingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colors.background
                ) {
                    CertificationScreen(colors, deviceWidth)
                }
            }
        }
    }
}