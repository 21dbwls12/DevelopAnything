package com.example.developanything.basicchangingactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import com.example.developanything.ui.theme.DevelopAnythingTheme

class LeftActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevelopAnythingTheme {
                val currentNumber = 0

                AllScaffold(
                    title = "2번",
                    iconButton = { MoveBackIcon() },
                    content = {
                        Greeting(Icons.Filled.Lock, currentNumber)
                    },
                    currentNumber = currentNumber
                )
            }
        }
    }
}