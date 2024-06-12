package com.example.lowvisionaidsbachelorthesis.components

import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lowvisionaidsbachelorthesis.R
import com.example.lowvisionaidsbachelorthesis.textToSpeech.TTS
import com.example.lowvisionaidsbachelorthesis.tflite.CameraPreview
import com.example.lowvisionaidsbachelorthesis.tflite.Classification
import com.example.lowvisionaidsbachelorthesis.ui.theme.Black
import com.example.lowvisionaidsbachelorthesis.ui.theme.White

@Composable
fun ScanningScreen(navController: NavHostController, controller: LifecycleCameraController, classifications: List<Classification>, textToSpeech: TTS) {

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {//relativna pozicija
        val screenHeight = maxHeight
        val desiredHeight = screenHeight * 0.8f

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
                Text(
                    text = it.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White)
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
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
fun extractUnit(input: String): String{
    val regex = "[a-zA-Z]+".toRegex()
    val matchResult = regex.find(input)
    var unit: String = ""
    if (matchResult != null) {
        unit = matchResult.value
    }

    if(unit == "f") return stringResource(id = R.string.phenings)
    else if(unit == "KM") return stringResource(id = R.string.KM)
    return ""
}