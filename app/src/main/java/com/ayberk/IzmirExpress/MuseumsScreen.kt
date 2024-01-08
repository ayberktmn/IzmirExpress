package com.ayberk.IzmirExpress

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayberk.IzmirExpress.model.Onemliyer
import com.ayberk.IzmirExpress.ui.theme.blue
import com.ayberk.IzmirExpress.viewmodel.DataViewModel
import com.ayberk.izmirilkyardim.R


@Composable
fun Muzeler(
    navHostController: NavHostController,
) {
    MuseumsList(navHostController = navHostController)
}

@Composable
fun MuseumsList(
    navHostController: NavHostController,
    viewModel: DataViewModel = hiltViewModel()
) {
    val museumsList by remember { viewModel.museumsList }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }

    MuseumsItemGrid(museums = museumsList,navHostController = navHostController)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator(color = blue)
        }
        if(errorMessage.isNotEmpty()) {
            RetryView(error = errorMessage) {
                viewModel.loadMuseums()
            }
        }
    }
}
@Composable
fun MuseumsItemGrid(navHostController: NavHostController, museums: List<Onemliyer>) {
    LazyVerticalGrid(GridCells.Fixed(1)) {
        items(museums) { item ->
            MuseumsRow(navHostController = navHostController, museums = item)
        }
    }
}

@Composable
fun MuseumsRow(navHostController: NavHostController, museums: Onemliyer) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(blue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.clocktower),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    text = museums.ADI,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }

            if (expanded) {
                Text(
                    text = museums.ACIKLAMA,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(start = 8.dp)
                        .padding(end = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = museums.ILCE,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    textAlign = TextAlign.End
                )
                IconButton(
                    onClick = { expanded = false },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally) // İkonu yatayda ortalamak için
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null,
                        tint = Color.White // Beyaz renkli olacak
                    )
                }
            }  else {
                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally) // İkonu yatayda ortalamak için
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color.White // Beyaz renkli olacak
                    )
                }
            }
        }
    }
}

@Composable
fun RetryView(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(error, color = Color.Red, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}

