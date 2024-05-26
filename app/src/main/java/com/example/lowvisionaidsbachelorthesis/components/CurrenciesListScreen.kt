@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lowvisionaidsbachelorthesis.components

import com.google.gson.Gson
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.lowvisionaidsbachelorthesis.ui.theme.Black
import com.example.lowvisionaidsbachelorthesis.ui.theme.White

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lowvisionaidsbachelorthesis.exchangeRatesAPI.ExchangeRatesViewModel
import com.example.lowvisionaidsbachelorthesis.ui.theme.Gray

import androidx.compose.runtime.livedata.observeAsState
import com.example.lowvisionaidsbachelorthesis.MainActivity


@Composable
fun CurrenciesListScreen(navController: NavHostController, exchangeRates: Map<String, Double>?) {

    //val exchangeRatesState = remember { mutableStateOf<Map<String, Double>?>(null) }

   /* LaunchedEffect(Unit) {
        viewModel.fetchExchangeRates("BAM")
        exchangeRatesState.value = viewModel.exchangeRates.value
        println("FETCHED: ${exchangeRatesState.value}")

    }*/

    //val exchangeRates by viewModel.exchangeRates.observeAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Odaberite željenu valutu",
                color = White,
                style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 40.dp)
            )
            InnerScreenCurrency(navController, exchangeRates)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InnerScreenCurrency(navController: NavHostController, exchangeRates: Map<String, Double>?) {
    var searchText by remember { mutableStateOf(TextFieldValue()) }

    // Create a list of currency codes from the observed exchange rates
    val currencyCodes = exchangeRates?.keys?.toList() ?: emptyList()

    // Filter the currency codes based on the search text
    val filteredCurrencies = remember(searchText.text) {
        currencyCodes.filter {
            it.contains(searchText.text, ignoreCase = true)
        }
    }

    Surface(
        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
        modifier = Modifier.fillMaxSize(),
        color = Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
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
                    ) {
                        Column(
                            modifier = Modifier.padding(13.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Link()

                            Scaffold(
                                backgroundColor = White,
                                topBar = {
                                    TopAppBar(
                                        backgroundColor = Gray,
                                        modifier = Modifier
                                            .padding(0.dp, 10.dp)
                                            .fillMaxWidth(),
                                        title = {
                                            androidx.compose.material.TextField(
                                                value = searchText,
                                                onValueChange = { searchText = it },
                                                placeholder = { Text("Search") },
                                                modifier = Modifier.fillMaxWidth(),
                                                leadingIcon = {
                                                    androidx.compose.material.Icon(
                                                        Icons.Filled.Search,
                                                        contentDescription = null
                                                    )
                                                },
                                                textStyle = TextStyle(color = White)
                                            )
                                        }
                                    )
                                }
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    LazyColumn(
                                        modifier = Modifier.height(400.dp)
                                    ) {
                                        items(filteredCurrencies.size) { index ->
                                            SearchResultItem(
                                                text = filteredCurrencies[index],
                                                isLastItem = index == filteredCurrencies.lastIndex,
                                                onClick = {
                                                    //println("Clicked on currency: ${filteredCurrencies[index]}")
                                                    navController.navigate("ConversionScreen/${exchangeRates?.get(filteredCurrencies[index])}")
                                                }
                                            )
                                        }

                                        //println("FILTERED CURRENCIES $filteredCurrencies")
                                    }
                                    BottomNavigation(navController = navController, "conversion")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultItem(text: String, isLastItem: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .then(if (!isLastItem) Modifier.padding(bottom = 8.dp) else Modifier)
            .clickable { onClick() },
        elevation = 4.dp
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}


@Composable
fun BottomNavigation(navController: NavHostController, screenActivity: String){
    var conversionEnabled: Boolean = true
    var scanningEnabled: Boolean = true

    if(screenActivity == "scanning") scanningEnabled = false
    if(screenActivity == "conversion") conversionEnabled = false
    //Spacer(Modifier.height(80.dp))
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navController.navigate("WelcomeScreen") },
                modifier = Modifier
                    .width(180.dp)
                    .height(70.dp),
                enabled = scanningEnabled,
                colors = ButtonDefaults.buttonColors(Black),
                shape = RoundedCornerShape(bottomStart = 20.dp, topStart = 20.dp, topEnd = 90.dp)
            ) {
                Text(
                    text = "Skeniranje",
                    color = White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 15.dp),
                    style = TextStyle(
                        fontSize = 21.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }

            Spacer(Modifier.width(2.dp))

            Button(
                onClick = { },
                enabled = conversionEnabled,
                modifier = Modifier
                    .width(180.dp)
                    .height(70.dp),
                colors = ButtonDefaults.buttonColors(Gray),
                shape = RoundedCornerShape(bottomStart = 90.dp, topEnd = 20.dp, bottomEnd = 20.dp)
            ) {
                Text(
                    text = "Konverzija",
                    color = Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp),
                    style = TextStyle(
                        fontSize = 21.sp,
                        textAlign = TextAlign.Center)
                )
            }
        }
    }
}
//attributions
@Composable
fun Link() {
    val mAnnotatedLinkString = buildAnnotatedString {

        //val mStr = "Rates By Exchange Rate API"
        val mStr = "Rates By Exchange Rate API www.exchangerate-api.com"
        val mStartIndex = mStr.indexOf("Rates")
        val mEndIndex = mStartIndex + 26

        append(mStr)
        addStyle(
            style = SpanStyle(
                color = Black,
                //textDecoration = TextDecoration.Underline,
                fontSize = 8.sp
            ), start = mStartIndex, end = mEndIndex
        )

        /* addStringAnnotation(
             tag = "URL",
             annotation = "https://www.exchangerate-api.com",
             start = mStartIndex,
             end = mEndIndex
         )*/
    }

    //val mUriHandler = LocalUriHandler.current

    Column(Modifier.height(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = mAnnotatedLinkString)
        /*ClickableText(
            text = mAnnotatedLinkString,
            onClick = {
                mAnnotatedLinkString
                    .getStringAnnotations("URL", it, it)
                    .firstOrNull()?.let { stringAnnotation ->
                        mUriHandler.openUri(stringAnnotation.item)
                    }
            }
        )*/
    }
}


