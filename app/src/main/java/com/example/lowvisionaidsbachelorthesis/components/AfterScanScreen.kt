package com.example.lowvisionaidsbachelorthesis.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.lowvisionaidsbachelorthesis.R
import com.example.lowvisionaidsbachelorthesis.database_dao.ScannedMoney
import com.example.lowvisionaidsbachelorthesis.database_dao.ScannedMoneyRepository
import com.example.lowvisionaidsbachelorthesis.ui.theme.Linen
import com.example.lowvisionaidsbachelorthesis.ui.theme.Martinique
import com.example.lowvisionaidsbachelorthesis.ui.theme.Smoky
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AfterScanScreen(navController: NavHostController, lastValue: String) {
    val roundedValue = BigDecimal(lastValue).setScale(2)
    val customFont = FontFamily(
        Font(R.font.roboto_mono)
    )
    val customNumberFont = FontFamily(
        Font(R.font.poppins)
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Martinique
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
                        .background(Linen)
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
                                .background(Smoky)
                        ){
                            Column(
                                modifier = Modifier.padding(13.dp).fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.last_scanned_value),
                                    color = Linen,
                                    fontFamily = customFont,
                                    style = TextStyle(fontSize = 20.sp),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(start = 35.dp, end = 35.dp, top = 15.dp)
                                )
                                Row(
                                    Modifier.semantics(mergeDescendants = true) {}
                                ) {
                                    Text(
                                        text = roundedValue.toString(),
                                        color = Linen,
                                        fontFamily = customNumberFont,
                                        style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    Text(
                                        text = "KM",
                                        color = Linen,
                                        fontFamily = customFont,
                                        style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(35.dp))
                        Button(
                            onClick = {
                                navController.navigate("ScanningScreen") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(Smoky),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.continue_scanning),
                                color = Linen,
                                fontFamily = customFont,
                                modifier = Modifier.padding(10.dp),
                                style = TextStyle(fontSize = 17.sp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                navController.navigate("WelcomeScreen")
                                      },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(Smoky),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.end_scanning),
                                color = Linen,
                                fontFamily = customFont,
                                modifier = Modifier.padding(10.dp),
                                style = TextStyle(fontSize = 17.sp)
                            )
                        }
                        BottomNavigation(navController = navController, screenActivity = "scanning")
                    }
                }
            }
        }
    }
}