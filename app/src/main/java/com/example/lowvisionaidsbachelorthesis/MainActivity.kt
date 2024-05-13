package com.example.lowvisionaidsbachelorthesis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.lowvisionaidsbachelorthesis.components.*
import com.example.lowvisionaidsbachelorthesis.ui.theme.PrimaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrimaryTheme {
               SearchScreen()
            }
        }
    }
}