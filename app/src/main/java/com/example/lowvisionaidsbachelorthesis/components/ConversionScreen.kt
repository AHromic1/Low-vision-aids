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
import com.example.lowvisionaidsbachelorthesis.ui.theme.Black
import com.example.lowvisionaidsbachelorthesis.ui.theme.Gray
import com.example.lowvisionaidsbachelorthesis.ui.theme.White

@Preview
@Composable
fun ConversionScreen() {
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
                                modifier = Modifier.padding(13.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = CenterHorizontally
                            ) {
                                Text(
                                    text = "Konvertovana vrijednost iznosi:",
                                    color = White,
                                    style = TextStyle(fontSize = 20.sp),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(start = 35.dp, end = 35.dp, top = 15.dp)
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text =  " KM",
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
                            onClick = { /* Nastavi skeniranje */ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(Black),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Nova konverzija",
                                color = White,
                                modifier = Modifier.padding(10.dp),
                                style = TextStyle(fontSize = 17.sp)
                            )
                        }

                        ////////
                        Spacer(modifier = Modifier.height(400.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { /* Nastavi skeniranje */ },
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
                                onClick = { /* Novo skeniranje */ },
                                modifier = Modifier
                                    .width(165.dp)
                                    .height(70.dp),
                                colors = ButtonDefaults.buttonColors(Gray),
                                shape = RoundedCornerShape(bottomStart = 90.dp, topEnd = 20.dp, bottomEnd = 20.dp)
                            ) {
                                Text(
                                    text = "Konverzija",
                                    color = White,
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
}