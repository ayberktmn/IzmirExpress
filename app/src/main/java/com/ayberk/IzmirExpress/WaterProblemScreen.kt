package com.ayberk.IzmirExpress

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayberk.IzmirExpress.model.WaterProblemItem
import com.ayberk.IzmirExpress.ui.theme.blue
import com.ayberk.IzmirExpress.ui.theme.red
import com.ayberk.IzmirExpress.ui.theme.waterLoadingcolor
import com.ayberk.IzmirExpress.ui.theme.white
import com.ayberk.IzmirExpress.viewmodel.DataViewModel
import com.ayberk.izmirilkyardim.R
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WaterProblemScreen(navHostController: NavHostController) {
    WaterProblemList(navHostController)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WaterProblemList(navHostController: NavHostController,viewModel: DataViewModel = hiltViewModel()) {
    val waterProblemList by remember { viewModel.waterProblemList }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }

    WaterProblemItemGrid(navHostController,waterProblemList)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator(color =waterLoadingcolor)
        }
        if(errorMessage.isNotEmpty()) {
            RetryView(error = errorMessage) {
                viewModel.loadPharmacy()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WaterProblemItemGrid(navHostController: NavHostController,waterProblemItem: List<WaterProblemItem>) {
    LazyVerticalGrid(GridCells.Fixed(2)){
        items(waterProblemItem){item ->
            WaterProblemItemList(navHostController,waterProblemItem = item)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WaterProblemItemList(navHostController: NavHostController, waterProblemItem: WaterProblemItem) {

    var dateTimeString by remember { mutableStateOf(waterProblemItem.KesintiTarihi) }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val dateTime = LocalDateTime.parse(dateTimeString, formatter)

    date = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))

    var isWaterDetailsClicked by remember { mutableStateOf(false) }
    var isNoWaterTimeClicked by remember { mutableStateOf(false) }

    val customFontFamily = FontFamily(
        Font(R.font.inter)
    )

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable {
                isWaterDetailsClicked = true
            }
            .shadow(8.dp, shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(white)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )  {
            if (isWaterDetailsClicked) {
                AlertDialog(
                    onDismissRequest = {
                        isWaterDetailsClicked = false
                    },
                    confirmButton = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.nowater),
                                contentDescription = "nowater",
                                modifier = Modifier
                                    .size(100.dp)
                            )
                            Text(
                                text = waterProblemItem.Tip,
                                textAlign = TextAlign.Center,
                                color = blue,
                                fontFamily = customFontFamily,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton(onClick = { isNoWaterTimeClicked = true }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = null,
                                )
                            }
                            Text(
                                text = date,
                                modifier = Modifier
                                    .padding(start = 2.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton(onClick = { /* Handle back button click */ }) {
                                Icon(
                                    imageVector  = Icons.Default.Home,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp)
                                )
                            }
                            Text(
                                text = waterProblemItem.Mahalleler,
                                modifier = Modifier
                                    .padding(start = 2.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton(onClick = { isNoWaterTimeClicked = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.clock),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp)
                                )
                            }
                            Text(
                                text = time,
                                modifier = Modifier
                                    .padding(start = 2.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                        Text(
                            text = waterProblemItem.Aciklama,
                            modifier = Modifier
                                .padding(start = 2.dp)
                                .align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center,
                            color = Color.Black

                        )
                        Text(
                            text = waterProblemItem.IlceAdi,
                            modifier = Modifier
                                .padding(start = 2.dp),
                            color = Color.Blue
                        )
                    },
                    containerColor = Color.White,
                )
            }
            Image(
                painter = painterResource(id = R.drawable.nowater),
                contentDescription = "nowater",
                modifier = Modifier
                    .size(80.dp)
            )
            Text(
                text = waterProblemItem.Tip,
                textAlign = TextAlign.Center,
                color = blue,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { isNoWaterTimeClicked = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                    )
                }
                Spacer(modifier = Modifier.width(4.dp)) // Özel bir boşluk ekleyin
                Text(
                    text = date,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { isNoWaterTimeClicked = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp)) // Özel bir boşluk ekleyin
                Text(
                    text = time,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }
            if(isNoWaterTimeClicked){
                AlertDialog(
                    onDismissRequest = { isNoWaterTimeClicked = false},
                    confirmButton = {
                        Text(
                            text = waterProblemItem.KesintiSuresi,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily.Default,
                            color = Color.Black
                        )
                    }
                )
            }
            Text(
                text = waterProblemItem.IlceAdi,
                modifier = Modifier
                    .padding(start = 2.dp)
                    .padding(top = 4.dp)
            )
        }
    }
}

