package com.example.lowvisionaidsbachelorthesis

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.lowvisionaidsbachelorthesis.components.*
import com.example.lowvisionaidsbachelorthesis.database.ScannedMoney
import com.example.lowvisionaidsbachelorthesis.exchangeRatesAPI.ExchangeRatesViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import com.example.lowvisionaidsbachelorthesis.database.ScannedMoneyRepository.Companion.writeToDB


class MainActivity : ComponentActivity() {
    private val exchangeRatesViewModel by viewModels<ExchangeRatesViewModel>()
    lateinit var textToSpeech: TextToSpeech

private fun addToDatabase(value: ScannedMoney) {
    lifecycleScope.launch {
        try {
            val result = writeToDB(applicationContext, value)
            println("Write to DB result: $result")
        } catch (error: Throwable) {
            println("Error: ${error.message}")
            error.printStackTrace()
        }
    }
}

    private var storedExchangeRates: Map<String, Double>? = null
    private var dateTime: LocalDateTime? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //textToSpeech = TextToSpeech(this, this)

        dateTime = LocalDateTime.now()

        lifecycleScope.launch {
            val sharedPreferences: CustomSharedPreferences = CustomSharedPreferences(applicationContext)
            if(hasOneDayElapsed(sharedPreferences)) {
                exchangeRatesViewModel.fetchExchangeRates("BAM")
                exchangeRatesViewModel.exchangeRates.observe(this@MainActivity) { rates ->
                    rates?.let {
                        sharedPreferences.saveMap("exchange_rates", it)
                        storedExchangeRates = sharedPreferences.getMap("exchange_rates")
                        //println("STORED IN MAIN: $storedExchangeRates")
                    }
                }
            }
        }
        val customSharedPreferences: CustomSharedPreferences = CustomSharedPreferences(applicationContext)
        storedExchangeRates = customSharedPreferences.getMap("exchange_rates")

        ////database test
        lifecycleScope.launch {
            val scannedMoney = ScannedMoney(1, 100.0)
            addToDatabase(scannedMoney)
        }



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
                    CurrenciesListScreen(navController = navController, exchangeRates = storedExchangeRates)
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

    class CustomSharedPreferences(context: Context) {
        private val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        fun saveMap(key: String, map: Map<String, Double>) {
            val gson = Gson()
            val json = gson.toJson(map)
            sharedPreferences.edit().putString(key, json).apply()
        }

        fun getMap(key: String): Map<String, Double>? {
            val gson = Gson()
            val json = sharedPreferences.getString(key, null)
            val type = object : TypeToken<Map<String, Double>>() {}.type
            return gson.fromJson(json, type)
        }

        fun getDateTime(key: String): String? {
            return sharedPreferences.getString(key, null)
        }

        fun putDateTime(key: String, value: String) {
            sharedPreferences.edit().putString(key, value).apply()
        }
    }

    /**
     * if it's true API will be called upon since 24 hours have expired
     */
    private fun hasOneDayElapsed(customSharedPreferences: CustomSharedPreferences): Boolean {
        val formatter = DateTimeFormatter.ISO_DATE
        val lastRunDate = customSharedPreferences.getDateTime("last_run_date")?.let { LocalDate.parse(it, formatter) }
        val today = LocalDate.now()

        return if (lastRunDate == null || today.isAfter(lastRunDate)) {
            customSharedPreferences.putDateTime("last_run_date", today.format(formatter))
            true
        } else {
            false
        }
    }

    /*private fun addScannedMoney() {
        val scannedMoney = ScannedMoney(0,  100.0)
        scannedMoneyViewModel.insert(scannedMoney)
    }*/
}