package com.example.lowvisionaidsbachelorthesis.components

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen() {
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
        "SGD",   // Singapore Dollar
        "GBP",  // British Pound Sterling
        "JPY",  // Japanese Yen
        "CAD",  // Canadian Dollar
        "AUD",  // Australian Dollar
        "CHF",  // Swiss Franc
        "CNY",  // Chinese Yuan
        "INR",  // Indian Rupee
        "SGD",
        "CHF",  // Swiss Franc
        "CNY",  // Chinese Yuan
        "INR",  // Indian Rupee
        "SGD",   // Singapore Dollar
        "GBP",  // British Pound Sterling
        "JPY",  // Japanese Yen
        "CAD",  // Canadian Dollar
        "AUD",  // Australian Dollar
        "CHF",  // Swiss Franc
        "CNY",  // Chinese Yuan
        "INR",  // Indian Rupee
        "SGD"   // Singapore Dollar// Singapore Dollar
    )

    val filteredCurrencies = remember(searchText) {currencies.filter { it.contains(searchText.text, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text("Search") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                        textStyle = TextStyle(color = Color.White)
                    )
                }
            )
        }
    ) {
        LazyColumn {
            items(filteredCurrencies.size) { index ->
                Text(filteredCurrencies[index], modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun SearchResultItem(text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click on search result */ },
        elevation = 4.dp
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            fontSize = 18.sp
        )
    }
}
}
