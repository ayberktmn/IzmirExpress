package com.ayberk.IzmirExpress

import android.provider.DocumentsContract
import android.text.style.TextAppearanceSpan
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.ayberk.IzmirExpress.model.Activitys
import com.ayberk.IzmirExpress.model.ActivitysItem
import com.ayberk.IzmirExpress.model.Onemliyer
import com.ayberk.IzmirExpress.ui.theme.blue
import com.ayberk.IzmirExpress.viewmodel.DataViewModel
import com.ayberk.izmirilkyardim.R
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Whitelist
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

@Composable
fun ActivityScreen(navHostController:NavHostController) {
    ActivitysList(navHostController)
}

@Composable
fun ActivitysList(navHostController: NavHostController,viewModel: DataViewModel = hiltViewModel()) {
    val activitysList by remember { viewModel.activitysList }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }

    ActivitysGridItem(navHostController,activitys = activitysList)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator(color = blue)
        }
        if(errorMessage.isNotEmpty()) {
            RetryView(error = errorMessage) {
                viewModel.loadActivitys()
            }
        }
    }
}

@Composable
fun ActivitysGridItem(navHostController:NavHostController,activitys: List<ActivitysItem>) {
    LazyVerticalGrid(GridCells.Fixed(2)) {
        items(activitys) { item ->
            ActivitysItem(navHostController,activitys = item)
        }
    }
}

@Composable
fun ActivitysItem(navHostController: NavHostController,activitys:ActivitysItem) {

    var dateTimeString by remember { mutableStateOf(activitys.EtkinlikBaslamaTarihi) }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val dateTime = LocalDateTime.parse(dateTimeString, formatter)

    date = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))

    var isClickedDetails by remember { mutableStateOf(false) }
    val ucrestsizmi = activitys.UcretsizMi

    val document: Document = Jsoup.parse(activitys.KisaAciklama)
    val cleanedHtml: String = Jsoup.clean(document.body().html(), Whitelist.none())

    Card (
        modifier = Modifier
            .size(305.dp)
            .padding(8.dp)
            .clickable {
                navHostController.navigate("activitydetails")
            }
            .shadow(8.dp, shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(Color.Black),
    ) {
        Image(
            painter = rememberImagePainter(data = activitys.Resim),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
                .size(150.dp),
            contentScale = ContentScale.FillHeight
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = activitys.Adi,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .padding(horizontal = 16.dp) // Genel yatay iç açıklık
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { isClickedDetails = true}) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                Text(
                    text = date,
                    modifier = Modifier
                        .padding(start = 2.dp, end = 2.dp)
                        .align(Alignment.CenterVertically),
                    color = Color.White
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = {isClickedDetails = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        tint = Color.White
                    )
                }
                Text(
                    text = time,
                    modifier = Modifier
                        .padding(start = 2.dp)
                        .align(Alignment.CenterVertically),
                    color = Color.White
                )
            }
        }
    }
    if (isClickedDetails) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AlertDialog(
                onDismissRequest = { isClickedDetails = false },
                confirmButton = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                isClickedDetails = true
                            }
                    ) {
                        Image(
                            painter = rememberImagePainter(data = activitys.Resim),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 8.dp)
                                .size(150.dp),
                            contentScale = ContentScale.FillHeight
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = activitys.Adi,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 8.dp)
                        )

                        Text(
                            text = cleanedHtml + "- Detaylar için tıklayınız...",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .clickable {
                                    navHostController.navigate("activitydetails")
                                }
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 8.dp),
                            maxLines = 11
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                                .padding(horizontal = 16.dp) // Genel yatay iç açıklık
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                IconButton(onClick = { }) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                                Text(
                                    text = date,
                                    modifier = Modifier
                                        .padding(start = 2.dp, end = 2.dp)
                                        .align(Alignment.CenterVertically),
                                    color = Color.White
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                IconButton(onClick = { }) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                                Text(
                                    text = activitys.EtkinlikMerkezi,
                                    modifier = Modifier
                                        .padding(start = 2.dp, end = 2.dp)
                                        .align(Alignment.CenterVertically),
                                    color = Color.White
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                IconButton(onClick = { }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.clock),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(20.dp),
                                        tint = Color.White
                                    )
                                }
                                Text(
                                    text = time,
                                    modifier = Modifier
                                        .padding(start = 2.dp)
                                        .align(Alignment.CenterVertically),
                                    color = Color.White
                                )
                            }
                            if (ucrestsizmi) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    IconButton(onClick = { }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.free),
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                    Text(
                                        text = "Ücretsiz",
                                        modifier = Modifier
                                            .padding(start = 2.dp, end = 2.dp)
                                            .align(Alignment.CenterVertically),
                                        color = Color.White
                                    )
                                }
                            } else {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    IconButton(onClick = { }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ticket),
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                                    Text(
                                        text = "Ücretli",
                                        modifier = Modifier
                                            .padding(start = 4.dp, end = 2.dp)
                                            .align(Alignment.CenterVertically),
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                },
                containerColor = Color(0xFF1944E0)
            )
        }
    }
}