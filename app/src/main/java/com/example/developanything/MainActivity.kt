package com.example.developanything

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.developanything.navigation.NavScreen
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
            val deviceWidth =
                width!! / ((this.resources.displayMetrics.densityDpi.toFloat()) / DisplayMetrics.DENSITY_DEFAULT)
            val navController = rememberNavController()
            val startDestination = remember {
                NavScreen.Certification.route
            }

            DevelopAnythingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colors.background
                ) {
                    NavHost(navController = navController, startDestination = startDestination) {
                        composable(NavScreen.Certification.route) {
                            CertificationScreen(
                                colors = colors,
                                deviceWidth = deviceWidth,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}