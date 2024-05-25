package com.example.lowvisionaidsbachelorthesis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
//import androidx.room.Room
import com.example.lowvisionaidsbachelorthesis.components.*
import com.example.lowvisionaidsbachelorthesis.database.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.lifecycleScope
//import com.example.lowvisionaidsbachelorthesis.database.ScannedMoneyRepository.Companion.writeToDB


import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val exchangeRatesViewModel by viewModels<ExchangeRatesViewModel>()

/*private fun addToDatabase(value: ScannedMoney) {
    lifecycleScope.launch {
        try {
            val result = writeToDB(applicationContext, value)
            println("Write to DB result: $result")
        } catch (error: Throwable) {
            println("Error: ${error.message}")
            error.printStackTrace()
        }
    }
}*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        ////database test
       /* lifecycleScope.launch {
            val scannedMoney = ScannedMoney(1, 100.0)
            addToDatabase(scannedMoney)
        }*/



      //  println("TOTAL VALUE2 $totalValue")

        /////

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
                composable(
                    "ConversionScreen/{value}",
                    arguments = listOf(navArgument("value") { type = NavType.FloatType })
                ) { backStackEntry ->
                    val value = backStackEntry.arguments?.getFloat("value") ?: 0.0f
                    ConversionScreen(navController = navController, value.toDouble())
                }
            }
        }
    }

   /* private fun addScannedMoney() {
        val scannedMoney = ScannedMoney(0,  100.0)
        scannedMoneyViewModel.insert(scannedMoney)
    }*/
}