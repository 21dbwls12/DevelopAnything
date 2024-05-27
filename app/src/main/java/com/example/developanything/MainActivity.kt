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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.developanything.navigation.NavScreen
import com.example.developanything.room.AppDatabase
import com.example.developanything.screen.CertificationScreen
import com.example.developanything.ui.theme.DarkColors
import com.example.developanything.ui.theme.DevelopAnythingTheme
import com.example.developanything.ui.theme.LightColors
import com.example.developanything.viewmodel.HabitRepository
import com.example.developanything.viewmodel.HabitViewModel
import com.example.developanything.viewmodel.HabitViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val colors = if (isSystemInDarkTheme()) DarkColors else LightColors
            // 사용자의 기기 화면 크기
//            val display = this.applicationContext?.resources?.displayMetrics
//            val width = display?.widthPixels
//            val deviceWidth =
//                width!! / ((this.resources.displayMetrics.densityDpi.toFloat()) / DisplayMetrics.DENSITY_DEFAULT)
            val navController = rememberNavController()
            val startDestination = remember {
                NavScreen.Certification.route
            }
            val db = AppDatabase.getDatabase(this)
            val habitDao = db.habitDao()
            val repository = HabitRepository(habitDao)
            val viewModelFactory = HabitViewModelFactory(repository)
            val viewModel = ViewModelProvider(this, viewModelFactory)[HabitViewModel::class.java]

            DevelopAnythingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colors.background
                ) {
                    NavHost(navController = navController, startDestination = startDestination) {
                        composable(NavScreen.Certification.route) {
                            CertificationScreen(
                                colors = colors,
                                viewModel = viewModel,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}