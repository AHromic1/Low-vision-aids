package com.example.lowvisionaidsbachelorthesis

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.lowvisionaidsbachelorthesis.components.*
import com.example.lowvisionaidsbachelorthesis.database_dao.ScannedMoney
import com.example.lowvisionaidsbachelorthesis.exchangeRatesAPI.ExchangeRatesViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

import com.example.lowvisionaidsbachelorthesis.database_dao.ScannedMoneyRepository.Companion.writeToDB
import com.example.lowvisionaidsbachelorthesis.textToSpeech.TTS
import com.example.lowvisionaidsbachelorthesis.tflite.Classification
import com.example.lowvisionaidsbachelorthesis.tflite.LandmarkImageAnalyzer
import com.example.lowvisionaidsbachelorthesis.tflite.TfLiteLandmarkClassifier


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private val exchangeRatesViewModel by viewModels<ExchangeRatesViewModel>()
    lateinit var textToSpeech: TextToSpeech
    private lateinit var tts: TTS

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
        //////////////////tflite//////////////////////
        if(!hasCameraPermission()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA), 0
            )
        }
        /////////////////////////////////////////////

        tts = TTS(this)

        textToSpeech = TextToSpeech(this, this)

        dateTime = LocalDateTime.now()

        lifecycleScope.launch {
            println("Launcheddd")
            val sharedPreferences = CustomSharedPreferences(applicationContext)
            //println("Has one day elapsed ${hasOneDayElapsed(sharedPreferences)}")
            val hasElapsed = hasOneDayElapsed(sharedPreferences)
            println("Has elapsed $hasElapsed")
            if(hasElapsed) {
                println("elapseddddd")
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
        /*lifecycleScope.launch {
            val scannedMoney = ScannedMoney(1, 100.0)
            addToDatabase(scannedMoney)
        }*/



      //  println("TOTAL VALUE2 $totalValue")

        /////

        //exchangeRatesViewModel.fetchExchangeRates("BAM")

        setContent {
            ////////////////////tflite//////////////////////////////////
            var classifications by remember {
                mutableStateOf(emptyList<Classification>())
            }
            val analyzer = remember {
                LandmarkImageAnalyzer(
                    classifier = TfLiteLandmarkClassifier(
                        context = applicationContext
                    ),
                    onResults = {
                        classifications = it
                        println("CLASSIFICATIONS $classifications")
                    }
                )
            }
            val controller = remember {
                LifecycleCameraController(applicationContext).apply {
                    setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                    setImageAnalysisAnalyzer(
                        ContextCompat.getMainExecutor(applicationContext),
                        analyzer
                    )
                }
            }
            ////////////////////////////////////////////////////////
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "WelcomeScreen") {
                composable("ScanningScreen") {
                    ScanningScreen(navController = navController, controller = controller, classifications = classifications, textToSpeech = tts)
                }
                composable("WelcomeScreen") {
                    WelcomeScreen(navController = navController, textToSpeech = tts)
                }
                composable("AfterScanScreen") {
                    AfterScanScreen(navController = navController)
                }
                composable("CurrenciesListScreen") {
                    CurrenciesListScreen(navController = navController, exchangeRates = storedExchangeRates, textToSpeech = tts)
                }
                composable(
                    "ConversionScreen/{value}",
                    arguments = listOf(navArgument("value") { type = NavType.FloatType })
                ) { backStackEntry ->
                    val value = backStackEntry.arguments?.getFloat("value") ?: 0.0f
                    ConversionScreen(navController = navController, value.toDouble(), textToSpeech = tts)
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
        println("LAST RUN DATE, $lastRunDate")
        val today = LocalDate.now()

        return if (lastRunDate == null || today.isAfter(lastRunDate)) {
            customSharedPreferences.putDateTime("last_run_date", today.format(formatter))
            true
        } else {
            false
        }
    }
    /////////////////////TTS
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val defaultLocale = Locale.getDefault()
            val result = textToSpeech.setLanguage(defaultLocale)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Initialization failed.", Toast.LENGTH_SHORT).show()
        }
    }

     fun speak(text: String) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }, 5000)
    }

    override fun onDestroy() {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
        super.onDestroy()
    }

    /*private fun addScannedMoney() {
        val scannedMoney = ScannedMoney(0,  100.0)
        scannedMoneyViewModel.insert(scannedMoney)
    }*/

    private fun hasCameraPermission() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}