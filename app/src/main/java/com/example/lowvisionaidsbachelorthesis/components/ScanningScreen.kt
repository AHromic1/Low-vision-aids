package com.example.lowvisionaidsbachelorthesis.components

import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lowvisionaidsbachelorthesis.R
import com.example.lowvisionaidsbachelorthesis.database_dao.ScannedMoney
import com.example.lowvisionaidsbachelorthesis.database_dao.ScannedMoneyRepository
import com.example.lowvisionaidsbachelorthesis.textToSpeech.TTS
import com.example.lowvisionaidsbachelorthesis.tflite.CameraPreview
import com.example.lowvisionaidsbachelorthesis.tflite.Classification
import com.example.lowvisionaidsbachelorthesis.ui.theme.Black
import com.example.lowvisionaidsbachelorthesis.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun ScanningScreen(navController: NavHostController, controller: LifecycleCameraController, classifications: List<Classification>, textToSpeech: TTS) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
            classifications.forEach {
                val number = extractNumber(it.name)
                val unit = extractUnit(it.name)
                textToSpeech.speak(number, 0)
                textToSpeech.speak(unit, 500)

                val formattedValue = formatValue(number.toDoubleOrNull(), unit)
                println("FORMATIRANA VR $formattedValue")
                var scannedMoney = ScannedMoney(1, formattedValue)
                var totalValue: Double? = 0.0

                coroutineScope.launch {
                    //dodati da se brise ako je kliknuto na new scan, a da se sabira ako je continue
                    //fetch from DB
                    try {

                        var valueFromDB = ScannedMoneyRepository.fetchFromDB(context)

                        //println("iz baze je ${formatValue(valueFromDB!![0].totalValue, unit)}")
                        if (valueFromDB != null) {
                            if (valueFromDB.isEmpty()) {
                                println("nema u bazi")
                                val result = ScannedMoneyRepository.writeToDB(context, scannedMoney)
                                println("Uspjesno dodavanje? $result")

                                valueFromDB = ScannedMoneyRepository.fetchFromDB(context)
                                println("iz baze 2 je $valueFromDB")
                            } else {


                                totalValue = formattedValue?.let { it1 ->
                                    valueFromDB!![0].totalValue?.plus(
                                        it1
                                    )
                                }
                                scannedMoney = ScannedMoney(1, totalValue)

                                println("ima u bazi")
                                val result = ScannedMoneyRepository.updateDB(context, scannedMoney)
                                println("Uspjesan update? $result")

                                valueFromDB = ScannedMoneyRepository.fetchFromDB(context)
                                println("iz baze3 je $valueFromDB")
                            }
                        }

                    } catch (error: Throwable) {
                        println("Error: ${error.message}")
                        error.printStackTrace()
                    }
                }

                Text(
                    text = it.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White)
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    color = Black
                )
            }
        }
        BottomNavigation(navController = navController, "scanningScreen")
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

@Composable   //zbog stringResource
fun extractUnit(input: String): String {
    val regex = "[a-zA-Z]+".toRegex()
    val matchResult = regex.find(input)
    var unit: String = ""
    if (matchResult != null) {
        unit = matchResult.value
    }

    if (unit == "f") return stringResource(id = R.string.phenings)
    else if (unit == "KM") return stringResource(id = R.string.KM)
    return ""
}

fun formatValue(value:Double?, unit:String): Double?{
    println("Unit je $unit")
    if(unit == "fenings") {
        println("feninzi su")
        if (value != null) {
            return value/100
        }
    }
    return value
}
