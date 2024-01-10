package com.ayberk.IzmirExpress

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.text.style.TextAlign
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
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

@Composable
fun ActivityScreen(navHostController:NavHostController) {
    ActivitysList()
}

@Composable
fun ActivitysList(viewModel: DataViewModel = hiltViewModel()) {
    val activitysList by remember { viewModel.activitysList }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }

    ActivitysGridItem(activitys = activitysList)

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
fun ActivitysGridItem(activitys: List<ActivitysItem>) {
    LazyVerticalGrid(GridCells.Fixed(2)) {
        items(activitys) { item ->
            ActivitysItem(activitys = item)
        }
    }
}

@Composable
fun ActivitysItem(activitys:ActivitysItem) {

    var dateTimeString by remember { mutableStateOf(activitys.EtkinlikBaslamaTarihi) }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val dateTime = LocalDateTime.parse(dateTimeString, formatter)

    date = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))

    Card (
        modifier = Modifier
            .size(305.dp)
            .padding(8.dp)
            .shadow(8.dp, shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(Color.Black),
    ){
        Card(
            modifier = Modifier
                .padding(8.dp)
                .size(150.dp)
                .align(Alignment.CenterHorizontally)
                .shadow(8.dp, shape = MaterialTheme.shapes.medium),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(Color.White),
        ) {
            Image(
                painter = rememberImagePainter(data = activitys.Resim),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillHeight
            )
        }
        Text(
            text = activitys.Adi,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Text(
                text = date,
                modifier = Modifier
                    .padding(start = 2.dp)
                    .align(Alignment.CenterVertically),
                color = Color.White
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = {  }) {
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