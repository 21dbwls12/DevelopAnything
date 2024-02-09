package com.example.developanything

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.developanything.basicchangingactivity.MiddleActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MiddleActivity::class.java)
        startActivity(intent)
        finish()
    }
}