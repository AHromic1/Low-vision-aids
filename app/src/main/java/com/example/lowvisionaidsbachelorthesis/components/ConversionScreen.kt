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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.lowvisionaidsbachelorthesis.R
import com.example.lowvisionaidsbachelorthesis.database_dao.ScannedMoneyRepository
import com.example.lowvisionaidsbachelorthesis.ui.theme.Linen
import com.example.lowvisionaidsbachelorthesis.ui.theme.Martinique
import com.example.lowvisionaidsbachelorthesis.ui.theme.Smoky
import java.math.RoundingMode
import java.math.BigDecimal


@Composable
fun ConversionScreen(navController: NavHostController, value: Double = 0.0, currency: String) {
    val context = LocalContext.current
    var totalScannedValue by remember { mutableStateOf(0.0) }
    var convertedValue by remember { mutableStateOf(BigDecimal(totalScannedValue).setScale(2, RoundingMode.CEILING)) }

    LaunchedEffect(context) {
        try {
            totalScannedValue = ScannedMoneyRepository.fetchFromDB(context)?.get(0)?.totalValue ?: 0.0
            convertedValue = BigDecimal(totalScannedValue * value).setScale(2, RoundingMode.CEILING)
        } catch (error: Throwable) {
            error.printStackTrace()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Martinique,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize().padding(top = 42.dp),
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
                                .background(Smoky),
                            contentAlignment = Alignment.Center
                        ){
                            Column(
                                modifier = Modifier.padding(13.dp),//.semantics(mergeDescendants = true){},
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(id = R.string.converted_value),
                                    color = Linen,
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

                                        text = "$convertedValue $currency",
                                        color = Linen,
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(
                                    elevation = 15.dp,
                                )
                                .background(
                                    color = Smoky,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            colors = ButtonDefaults.buttonColors(Smoky),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.new_conversion),
                                color = Linen,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .clickable(
                                        onClickLabel = stringResource(R.string.new_conversion),
                                        onClick = { navController.navigate("CurrenciesListScreen") }
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