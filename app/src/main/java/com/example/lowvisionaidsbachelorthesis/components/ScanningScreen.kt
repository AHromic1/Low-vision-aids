package com.example.lowvisionaidsbachelorthesis.components

import android.annotation.SuppressLint
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lowvisionaidsbachelorthesis.R
import com.example.lowvisionaidsbachelorthesis.database_dao.ScannedMoney
import com.example.lowvisionaidsbachelorthesis.database_dao.ScannedMoneyRepository
import com.example.lowvisionaidsbachelorthesis.textToSpeech.TTS
import com.example.lowvisionaidsbachelorthesis.tflite.CameraPreview
import com.example.lowvisionaidsbachelorthesis.tflite.Classification
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ScanningScreen(navController: NavHostController, controller: LifecycleCameraController, classifications: List<Classification>, textToSpeech: TTS) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {//relativna pozicija
        val screenHeight = maxHeight
        val desiredHeight = screenHeight * 0.85f

        CameraPreview(controller,
            Modifier
                .fillMaxWidth()
                .height(desiredHeight))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            if(classifications.size != 0) {
                val classification = classifications.lastOrNull()?.name!!
                val currentRoute = navBackStackEntry?.destination?.route
                val number = extractNumber(classification).toDoubleOrNull()
                val unit = extractUnit(classification)

                val value = formatValue(number, unit) ?: 0.00
                println("VRIJEDNOST $value")
                if (currentRoute?.startsWith("AfterScanScreen") != true)  {
                    coroutineScope.launch {
                        try {
                            var valueFromDB = ScannedMoneyRepository.fetchFromDB(context)
                            if (valueFromDB != null) {
                                valueFromDB = if (valueFromDB.isEmpty()) {
                                    ScannedMoneyRepository.writeToDB(context, ScannedMoney(1, value));
                                    ScannedMoneyRepository.fetchFromDB(context)
                                } else {
                                    val totalValue = value.let { it1 ->
                                        valueFromDB!![0].totalValue?.plus(
                                            it1
                                        )
                                    }
                                    val scannedMoney = ScannedMoney(1, totalValue)
                                    ScannedMoneyRepository.updateDB(context, scannedMoney)
                                    ScannedMoneyRepository.fetchFromDB(context)
                                }
                            }
                        } catch (error: Throwable) {
                            println("Error: ${error.message}")
                            error.printStackTrace()
                        }
                    }
                    navController.navigate(
                        "AfterScanScreen/${value}"
                    )
                }
            }
        }
    }
}

fun extractNumber(input: String): String {
    val regex = "\\d+".toRegex()
    val matchResult = regex.find(input)
    if (matchResult != null) {
        return matchResult.value
    }
    return ""
}

@Composable
fun extractUnit(input: String): String {
    val regex = "[a-zA-Z]+".toRegex()
    val matchResult = regex.find(input)
    var unit = ""
    if (matchResult != null) {
        unit = matchResult.value
    }

    if (unit == "f") return stringResource(id = R.string.phenings)
    else if (unit == "KM") return stringResource(id = R.string.KM)
    return ""
}

fun formatValue(value:Double?, unit:String): Double?{
    if(unit == "fenings") {
        if (value != null) {
            return value/100
        }
    }
    return value
}
