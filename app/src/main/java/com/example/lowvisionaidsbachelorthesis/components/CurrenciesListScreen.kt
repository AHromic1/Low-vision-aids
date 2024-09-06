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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.lowvisionaidsbachelorthesis.R
import com.example.lowvisionaidsbachelorthesis.ui.theme.LightLinen
import com.example.lowvisionaidsbachelorthesis.ui.theme.Linen
import com.example.lowvisionaidsbachelorthesis.ui.theme.Martinique

@Composable
fun CurrenciesListScreen(navController: NavHostController, exchangeRates: Map<String, Double>?) {
    val customFont = FontFamily(
        Font(R.font.roboto_mono)
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
            Text(
                text = stringResource(id = R.string.choose_currency_text),
                color = Linen,
                fontFamily = customFont,
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
        color = Martinique
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
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Link()
                                Scaffold(
                                    modifier = Modifier.height(70.dp),
                                backgroundColor = Linen,
                                topBar = {
                                    TopAppBar(
                                        backgroundColor = Linen,
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
                                                textStyle = TextStyle(color = Martinique)
                                            )
                                        }
                                    )
                                }
                            ) { }
                                Box(
                                    modifier = Modifier.height(330.dp),
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
    val customFont = FontFamily(
        Font(R.font.roboto_mono)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .then(if (!isLastItem) Modifier.padding(bottom = 8.dp) else Modifier)
            .clickable { onClick() },
        elevation = 4.dp,
        backgroundColor = LightLinen,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontFamily = customFont,
            modifier = Modifier.padding(16.dp),
            color = Martinique
        )
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
                color = Martinique,
                fontSize = 8.sp
            ), start = mStartIndex, end = mEndIndex
        )
    }

    Column(Modifier.height(20.dp), horizontalAlignment = CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = mAnnotatedLinkString)
    }
}


