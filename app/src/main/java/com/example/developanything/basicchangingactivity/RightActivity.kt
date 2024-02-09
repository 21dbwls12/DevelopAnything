package com.example.developanything.basicchangingactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import com.example.developanything.ui.theme.DevelopAnythingTheme

class RightActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevelopAnythingTheme {
                val currentNumber = 2

                AllScaffold(
                    title = "3번",
                    iconButton = { MoveBackIcon() },
                    content = {
                        Greeting(Icons.Filled.Favorite, currentNumber)
                    },
                    currentNumber = currentNumber
                )
            }
        }
    }
}