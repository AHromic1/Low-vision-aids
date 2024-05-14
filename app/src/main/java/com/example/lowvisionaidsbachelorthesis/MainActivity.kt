package com.example.lowvisionaidsbachelorthesis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lowvisionaidsbachelorthesis.components.*
import com.example.lowvisionaidsbachelorthesis.ui.theme.PrimaryTheme

class MainActivity : ComponentActivity() {
    // Create an instance of ExchangeRatesViewModel
    private val exchangeRatesViewModel by viewModels<ExchangeRatesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fetch exchange rates when MainActivity is created
        exchangeRatesViewModel.fetchExchangeRates("BAM")

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
                    CurrenciesListScreen(navController = navController, viewModel = exchangeRatesViewModel)
                }
                composable("ConversionScreen") {
                    ConversionScreen(navController = navController)
                }
            }
        }
    }
}