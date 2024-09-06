import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.draw.shadow
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
import androidx.navigation.NavHostController
import com.example.lowvisionaidsbachelorthesis.R
import com.example.lowvisionaidsbachelorthesis.components.BottomNavigation
import com.example.lowvisionaidsbachelorthesis.database_dao.ScannedMoneyRepository
import com.example.lowvisionaidsbachelorthesis.ui.theme.Black
import com.example.lowvisionaidsbachelorthesis.ui.theme.Linen
import com.example.lowvisionaidsbachelorthesis.ui.theme.Martinique
import com.example.lowvisionaidsbachelorthesis.ui.theme.Smoky
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Martinique
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.welcome),
                color = Linen,
                style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(top = 40.dp)
                    .clickable(
                        onClickLabel = stringResource(R.string.welcome),
                        onClick = { }
                    )
            )
            InnerScreen(navController)
        }
    }
}

@Composable
fun InnerScreen(navController: NavHostController) {
    val context = LocalContext.current
    var totalScannedValue by remember { mutableStateOf(0.0) }
    var roundedValue by remember { mutableStateOf(BigDecimal(totalScannedValue).setScale(2, RoundingMode.CEILING)) }
    val coroutineScope = rememberCoroutineScope()
    val customFont = FontFamily(
        Font(R.font.roboto_mono)
    )
    val customNumberFont = FontFamily(
        Font(R.font.poppins)
    )

    LaunchedEffect(context) {
        try {
            val valueFromDB = ScannedMoneyRepository.fetchFromDB(context)?.get(0)?.totalValue ?: 0.00
            totalScannedValue = valueFromDB
            roundedValue = BigDecimal(totalScannedValue).setScale(2, RoundingMode.HALF_EVEN)
        } catch (error: Throwable) {
            error.printStackTrace()
        }
    }

    Surface(
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
                            .clip(RoundedCornerShape(50.dp))
                            .background(Smoky),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(13.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(id = R.string.total_scanned_value),
                                color = Linen,
                                fontFamily = customFont,
                                style = TextStyle(fontSize = 20.sp),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(start = 35.dp, end = 35.dp, top = 15.dp)
                                    .clickable(
                                        onClickLabel = stringResource(R.string.total_scanned_value),
                                        onClick = { }
                                    )
                            )
                            Row(
                                Modifier.semantics(mergeDescendants = true) {}
                            ) {
                                Text(
                                    text = roundedValue.toString(),
                                    color = Linen,
                                    fontFamily = customNumberFont,
                                    style = TextStyle(
                                        fontSize = 50.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(8.dp)
                                )
                                Text(
                                    text = "KM",
                                    color = Linen,
                                    fontFamily = customFont,
                                    style = TextStyle(
                                        fontSize = 50.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(8.dp)
                                        .clickable(
                                            onClickLabel = stringResource(R.string.KM),
                                            onClick = { }
                                        )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(35.dp))
                    Button(
                        onClick = { navController.navigate("ScanningScreen") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 5.dp,
                            )
                            .background(
                                color = Smoky,
                                shape = RoundedCornerShape(8.dp)
                            ),
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
                            coroutineScope.launch {
                                goToNewScan(navController, context)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 5.dp,
                            )
                            .background(
                            color = Smoky,
                            shape = RoundedCornerShape(8.dp)
                        ),
                        colors = ButtonDefaults.buttonColors(Smoky),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.new_scan),
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

suspend fun goToNewScan(navController: NavHostController, context: Context) {
    try {
        ScannedMoneyRepository.deleteByIdFromDB(context, 1)
        navController.navigate("ScanningScreen")
    } catch (error: Throwable) {
        error.printStackTrace()
    }
}
