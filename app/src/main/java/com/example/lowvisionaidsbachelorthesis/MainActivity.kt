package com.example.lowvisionaidsbachelorthesis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lowvisionaidsbachelorthesis.components.*
import com.example.lowvisionaidsbachelorthesis.ui.theme.PrimaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "CurrenciesListScreen") {
                composable("Test") {
                    //Test(navController = navController)
                }
                composable("WelcomeScreen") {
                    WelcomeScreen(navController = navController)
                }
                composable("AfterScanScreen") {
                    AfterScanScreen(navController = navController)
                }
                composable("CurrenciesListScreen") {
                    CurrenciesListScreen(navController = navController)
                }
                composable("ConversionScreen") {
                    ConversionScreen(navController = navController)
                }
            }
        }
    }
}