@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lowvisionaidsbachelorthesis.components

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
import com.example.lowvisionaidsbachelorthesis.ui.theme.Gray

import androidx.compose.ui.res.stringResource
import com.example.lowvisionaidsbachelorthesis.R


@Composable
fun CurrenciesListScreen(navController: NavHostController, exchangeRates: Map<String, Double>?) {
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
                text = stringResource(id = R.string.choose_currency_text),
                color = White,
                style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 40.dp).clickable(
                    onClickLabel = stringResource(R.string.choose_currency),
                    onClick = { }
                )
            )
            InnerScreenCurrency(navController, exchangeRates)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InnerScreenCurrency(navController: NavHostController, exchangeRates: Map<String, Double>?) {
    var searchText by remember { mutableStateOf(TextFieldValue()) }

    val currencyCodes = exchangeRates?.keys?.toList() ?: emptyList()
    val filteredCurrencies = remember(searchText.text.trim()) {
        currencyCodes.filter {
            it.contains(searchText.text.trim(), ignoreCase = true)
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
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Link()
                                Scaffold(
                                    modifier = Modifier.height(70.dp),
                                backgroundColor = White,
                                topBar = {
                                    TopAppBar(
                                        backgroundColor = White,
                                        modifier = Modifier
                                            .padding(0.dp, 10.dp)
                                            .fillMaxWidth()
                                            .clickable(
                                                onClickLabel = stringResource(R.string.search_for_currency),
                                                onClick = { }
                                            ),
                                        title = {
                                            androidx.compose.material.TextField(
                                                value = searchText,
                                                onValueChange = { searchText = it },
                                                placeholder = { Text(stringResource(id = R.string.search)) },
                                                modifier = Modifier.fillMaxWidth(),
                                                leadingIcon = {
                                                    androidx.compose.material.Icon(
                                                        Icons.Filled.Search,
                                                        contentDescription = null
                                                    )
                                                },
                                                textStyle = TextStyle(color = Black)
                                            )
                                        }
                                    )
                                }
                            ) { }
                                Box(
                                    modifier = Modifier.height(330.dp)
                                ) {
                                    LazyColumn(
                                        modifier = Modifier.height(330.dp)
                                    ) {
                                        items(filteredCurrencies.size) { index ->
                                            SearchResultItem(
                                                text = filteredCurrencies[index],
                                                isLastItem = index == filteredCurrencies.lastIndex,
                                                onClick = {
                                                    navController.navigate(
                                                        "ConversionScreen/${
                                                            exchangeRates?.get(
                                                                filteredCurrencies[index]
                                                            )
                                                        }/${
                                                            filteredCurrencies[index]
                                                        }"
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                            BottomNavigation(navController = navController, "conversion")
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

    var conversionButtonColor = Black
    var scanningButtonColor = Black
    var conversionTextColor = White
    var scanningTextColor = White


    if(screenActivity == "scanning"){
        scanningEnabled = false
        scanningButtonColor = Gray
        scanningTextColor = Black
    }
    else if(screenActivity == "conversion"){
        conversionEnabled = false
        conversionButtonColor = Gray
        conversionTextColor = Black
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                onClickLabel = stringResource(R.string.conversion_bttn),
                onClick = {}
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .clickable(
                    onClickLabel = stringResource(R.string.conversion_bttn),
                    onClick = {}
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navController.navigate("WelcomeScreen") },
                modifier = Modifier
                    .width(180.dp)
                    .height(70.dp)
                    .clickable(
                        onClickLabel = stringResource(R.string.scanning_bttn),
                        onClick = {navController.navigate("WelcomeScreen")}
                    ),
                enabled = scanningEnabled,
                colors = ButtonDefaults.buttonColors(scanningButtonColor),
                shape = RoundedCornerShape(bottomStart = 20.dp, topStart = 20.dp, topEnd = 90.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.scanning),
                    color = scanningTextColor,
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
                onClick = { navController.navigate("CurrenciesListScreen")},
                enabled = conversionEnabled,
                modifier = Modifier
                    .width(180.dp)
                    .height(70.dp)
                    .clickable(
                        onClickLabel = stringResource(R.string.conversion_bttn),
                        onClick = {navController.navigate("CurrenciesListScreen")}
                    ),
                colors = ButtonDefaults.buttonColors(conversionButtonColor),
                shape = RoundedCornerShape(bottomStart = 90.dp, topEnd = 20.dp, bottomEnd = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.conversion),
                    color = conversionTextColor,
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
        val mStr = "Rates By Exchange Rate API www.exchangerate-api.com"
        val mStartIndex = mStr.indexOf("Rates")
        val mEndIndex = mStartIndex + 26

        append(mStr)
        addStyle(
            style = SpanStyle(
                color = Black,
                fontSize = 8.sp
            ), start = mStartIndex, end = mEndIndex
        )
    }

    Column(Modifier.height(20.dp), horizontalAlignment = CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = mAnnotatedLinkString)
    }
}


