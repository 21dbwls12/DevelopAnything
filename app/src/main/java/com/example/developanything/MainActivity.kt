package com.example.developanything

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.developanything.screen.MainScreen
import com.example.developanything.ui.theme.DevelopAnythingTheme
import com.example.developanything.viewModel.KtorViewModel
import com.example.developanything.viewModel.RetrofitViewModel

class MainActivity : ComponentActivity() {
    private val ktorViewModel: KtorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevelopAnythingTheme {
                val retrofitViewModel = ViewModelProvider(this).get(RetrofitViewModel::class.java)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(retrofitViewModel, ktorViewModel)
                }
            }
        }
    }
}