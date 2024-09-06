package com.example.lowvisionaidsbachelorthesis.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lowvisionaidsbachelorthesis.R
import com.example.lowvisionaidsbachelorthesis.ui.theme.Gray
import com.example.lowvisionaidsbachelorthesis.ui.theme.Linen
import com.example.lowvisionaidsbachelorthesis.ui.theme.Martinique

@Composable
fun BottomNavigation(navController: NavHostController, screenActivity: String){
    var conversionEnabled = true
    var scanningEnabled = true

    var conversionButtonColor = Martinique
    var scanningButtonColor = Martinique
    var conversionTextColor = Linen
    var scanningTextColor = Linen


    if(screenActivity == "scanning"){
        scanningEnabled = false
        scanningButtonColor = Gray
        scanningTextColor = Martinique
    }
    else if(screenActivity == "conversion"){
        conversionEnabled = false
        conversionButtonColor = Gray
        conversionTextColor = Martinique
    }

    val customFont = FontFamily(
        Font(R.font.roboto_mono)
    )

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
                    )
                ,
                enabled = scanningEnabled,
                colors = ButtonDefaults.buttonColors(scanningButtonColor),
                shape = RoundedCornerShape(bottomStart = 20.dp, topStart = 20.dp, topEnd = 90.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.scanning),
                    color = scanningTextColor,
                    fontFamily = customFont,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 15.dp),
                    style = TextStyle(
                        fontSize = 21.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal, // Adjust font weight if necessary

                    ),
                    maxLines = 1,  // Limit to a single line
                    overflow = TextOverflow.Visible,  // Handle text overflow by showing "..."
                    softWrap = false,  // Disable wrapping
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
                    fontFamily = customFont,
                    style = TextStyle(
                        fontSize = 21.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal, // Adjust font weight if necessary
                    ),
                    maxLines = 1,  // Limit to a single line
                    overflow = TextOverflow.Visible,  // Handle text overflow by showing "..."
                    softWrap = false,  // Disable wrapping
                )
            }
        }
    }
}