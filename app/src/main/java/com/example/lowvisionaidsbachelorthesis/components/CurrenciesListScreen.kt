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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.lowvisionaidsbachelorthesis.ui.theme.Black
import com.example.lowvisionaidsbachelorthesis.ui.theme.White

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.lowvisionaidsbachelorthesis.ui.theme.Gray

@Composable
fun CurrenciesListScreen(navController: NavHostController) {


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
            Text(text = "Odaberite željenu valutu",
                color = White,
                style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 40.dp)
            )
            InnerScreenCurrency(navController)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InnerScreenCurrency(navController: NavHostController) {
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
                    ) {    //search bar
                        Column(
                            modifier = Modifier.padding(13.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = CenterHorizontally
                        ) {  //search bar
                            Link()
                            var searchText by remember { mutableStateOf(TextFieldValue()) }
                            val currencies = arrayOf(
                                "USD",  // United States Dollar
                                "EUR",  // Euro
                                "GBP",  // British Pound Sterling
                                "JPY",  // Japanese Yen
                                "CAD",  // Canadian Dollar
                                "AUD",  // Australian Dollar
                                "CHF",  // Swiss Franc
                                "CNY",  // Chinese Yuan
                                "INR",  // Indian Rupee
                                "SGD"   // Singapore Dollar
                            )

                            val filteredCurrencies = remember(searchText) {
                                currencies.filter {
                                    it.contains(
                                        searchText.text,
                                        ignoreCase = true
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier.height(560.dp),
                                contentAlignment = Alignment.Center
                            ) {
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
                                            modifier = Modifier.height(480.dp)
                                        ) {
                                            items(filteredCurrencies.size) { index ->
                                                SearchResultItem(
                                                    text = filteredCurrencies[index],
                                                    isLastItem = index == filteredCurrencies.lastIndex
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(Modifier.width(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.navigate("WelcomeScreen") },
                    modifier = Modifier
                        .width(165.dp)
                        .height(70.dp),
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
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                Spacer(Modifier.width(2.dp))
                Button(
                    onClick = { },
                    enabled = false,
                    modifier = Modifier
                        .width(165.dp)
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
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center)
                    )
                    }
                }
                }
             }
        }
    }
}

@Composable
fun SearchResultItem(text: String, isLastItem: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .then(if (!isLastItem) Modifier.padding(bottom = 8.dp) else Modifier),
        elevation = 4.dp
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

//attributions
@Composable
fun Link() {
    val mAnnotatedLinkString = buildAnnotatedString {

        //val mStr = "Rates By Exchange Rate API"
        val mStr = "Rates By Exchange Rate API: www.exchangerate-api.com"
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


