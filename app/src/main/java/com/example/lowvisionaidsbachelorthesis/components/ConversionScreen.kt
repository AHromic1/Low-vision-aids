package com.example.lowvisionaidsbachelorthesis.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.lowvisionaidsbachelorthesis.ui.theme.Black
import com.example.lowvisionaidsbachelorthesis.ui.theme.White
import androidx.navigation.NavHostController
import com.example.lowvisionaidsbachelorthesis.R
import com.example.lowvisionaidsbachelorthesis.textToSpeech.TTS
import java.math.RoundingMode
import java.math.BigDecimal


@Composable
fun ConversionScreen(navController: NavHostController, value: Double = 0.0, textToSpeech: TTS) {
    textToSpeech.speak(stringResource(id = R.string.converted_value), 5000)
    val BD = BigDecimal(value)
    val roundedValue = BD.setScale(2, RoundingMode.CEILING)
    val KMString = stringResource(R.string.KM)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 70.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                        .background(White)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(50.dp))
                                .background(Black)
                        ){
                            Column(
                                modifier = Modifier.padding(13.dp),//.semantics(mergeDescendants = true){},
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(id = R.string.converted_value),
                                    color = White,
                                    style = TextStyle(fontSize = 20.sp),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(start = 35.dp, end = 35.dp, top = 15.dp)
                                        .clickable(
                                            onClickLabel = stringResource(R.string.converted_value),
                                            onClick = {}
                                        )
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(

                                        text = "$roundedValue $KMString",
                                        color = White,
                                        style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(35.dp))
                        Button(
                            onClick = { navController.navigate("CurrenciesListScreen") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(Black),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.new_conversion),
                                color = White,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable(
                                        onClickLabel = stringResource(R.string.new_conversion),
                                        onClick = { }
                                    ),
                                style = TextStyle(fontSize = 17.sp)
                            )
                        }
                        BottomNavigation(navController = navController, screenActivity = "conversion")
                    }
                }
            }
        }
    }
}