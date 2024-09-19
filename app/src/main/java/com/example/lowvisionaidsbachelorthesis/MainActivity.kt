package com.example.lowvisionaidsbachelorthesis

import WelcomeScreen
import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.lowvisionaidsbachelorthesis.components.*
import com.example.lowvisionaidsbachelorthesis.exchangeRatesAPI.ExchangeRatesViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*
import com.example.lowvisionaidsbachelorthesis.textToSpeech.TTS
import com.example.lowvisionaidsbachelorthesis.tflite.Classification
import com.example.lowvisionaidsbachelorthesis.tflite.LandmarkImageAnalyzer
import com.example.lowvisionaidsbachelorthesis.tflite.TfLiteLandmarkClassifier

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private val exchangeRatesViewModel by viewModels<ExchangeRatesViewModel>()
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var tts: TTS
    private var dateTime: LocalDateTime? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //tflite
        if(!hasCameraPermission()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA), 0
            )
        }

        tts = TTS(this)
        textToSpeech = TextToSpeech(this, this)
        dateTime = LocalDateTime.now()

        setContent {
            var storedExchangeRates by remember { mutableStateOf<Map<String, Double>?>(null) }

            LaunchedEffect(applicationContext) {
                val sharedPreferences = CustomSharedPreferences(applicationContext)
                val hasElapsed = hasOneDayElapsed(sharedPreferences)

                if(hasElapsed) {
                    exchangeRatesViewModel.fetchExchangeRates("BAM")
                    exchangeRatesViewModel.exchangeRates.observe(this@MainActivity) { rates ->
                        rates?.let {
                            sharedPreferences.saveMap("exchange_rates", it)
                            storedExchangeRates = sharedPreferences.getMap("exchange_rates")
                        }
                    }
                }
            }
            val customSharedPreferences = CustomSharedPreferences(applicationContext)
            storedExchangeRates = customSharedPreferences.getMap("exchange_rates")

            //tflite classification
            var classification by remember {
                mutableStateOf(emptyList<Classification>())
            }
            val analyzer = remember {
                LandmarkImageAnalyzer(
                    classifier = TfLiteLandmarkClassifier(
                        context = applicationContext
                    ),
                    onResults = {
                        if(it.isNotEmpty()) LastClassification.setLast(it[0])
                        classification += it
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

            //navigation
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "WelcomeScreen") {
                composable("ScanningScreen") {
                    ScanningScreen(navController = navController, controller = controller, classification = classification)
                }
                composable("WelcomeScreen") {
                    WelcomeScreen(navController = navController)
                }
                composable("AfterScanScreen/{last_value}",  arguments = listOf(navArgument("last_value") { type = NavType.StringType })) {
                        backStackEntry ->
                    val value = backStackEntry.arguments?.getString("last_value")?: "0.00 KM"
                    AfterScanScreen(navController = navController, value)
                }
                composable("CurrenciesListScreen") {
                    CurrenciesListScreen(navController = navController, exchangeRates = storedExchangeRates)
                }
                composable(
                    "ConversionScreen/{value}/{currency}",
                    arguments = listOf(
                        navArgument("value") { type = NavType.FloatType },
                        navArgument("currency") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val value = backStackEntry.arguments?.getFloat("value") ?: 0.0f
                    val currency = backStackEntry.arguments?.getString("currency") ?: ""
                    ConversionScreen(navController = navController, value.toDouble(), currency)
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

    //Text to speech
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

    override fun onDestroy() {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
        super.onDestroy()
    }

    private fun hasCameraPermission() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}