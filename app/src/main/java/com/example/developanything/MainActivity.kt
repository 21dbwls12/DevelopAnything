package com.example.developanything

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.developanything.ui.theme.DevelopAnythingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevelopAnythingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = NavRoutes.Greeting.route
                    ) {
                        composable(NavRoutes.Greeting.route) { GreetingScreen(navController) }
                        composable(NavRoutes.Home.route) { HomeScreen(navController) }
                        composable(NavRoutes.First.route) {
                            FirstScreen(
                                it.arguments?.getString("number") ?: ""
                            )
                        }
                        composable(NavRoutes.Second.route) {
                            SecondScreen(
                                it.arguments?.getString("number") ?: ""
                            )
                        }
                    }
                }
            }
        }
    }
}





