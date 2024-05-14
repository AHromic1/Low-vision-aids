package com.example.lowvisionaidsbachelorthesis.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.lowvisionaidsbachelorthesis.ui.theme.Black
import com.example.lowvisionaidsbachelorthesis.ui.theme.Gray
import com.example.lowvisionaidsbachelorthesis.ui.theme.White

@Composable
fun WelcomeScreen(navController: NavHostController) {
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
            Text(text = "Dobro došli!",
                color = White,
                style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 40.dp)
            )
            InnerScreen(navController)
        }
    }
}

@Composable
fun InnerScreen(navController: NavHostController) {
    Surface(
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
                modifier = Modifier.fillMaxHeight().fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)).background(White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(50.dp)).background(Black)
                    ) {
                        Column(
                            modifier = Modifier.padding(13.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Text(
                                text = "Ukupna skenirana vrijednost:",
                                color = White,
                                style = TextStyle(fontSize = 20.sp),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(start = 35.dp, end = 35.dp, top = 15.dp)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "100 KM",
                                    color = White,
                                    style = TextStyle(
                                        fontSize = 50.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(35.dp))
                    Button(
                        onClick = { /* Nastavi skeniranje */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Black),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Nastavi skeniranje",
                            color = White,
                            modifier = Modifier.padding(10.dp),
                            style = TextStyle(fontSize = 17.sp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { /* Novo skeniranje */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Black),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Novo skeniranje",
                            color = White,
                            modifier = Modifier.padding(10.dp),
                            style = TextStyle(fontSize = 17.sp)
                        )
                    }
                }
            }
        }
    }
}