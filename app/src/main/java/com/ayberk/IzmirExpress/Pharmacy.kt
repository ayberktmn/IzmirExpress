package com.ayberk.IzmirExpress

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayberk.IzmirExpress.model.PharmacyItem
import com.ayberk.IzmirExpress.ui.theme.blue
import com.ayberk.IzmirExpress.viewmodel.DataViewModel
import com.ayberk.izmirilkyardim.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Pharmacy(navHostController: NavHostController) {
    PharmacyList()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PharmacyList(viewModel: DataViewModel = hiltViewModel()) {
    val pharmacyList by remember { viewModel.pharmacyList }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }

    PharmacyItemGrid(pharmacyList)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator(color = Color.Red)
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
fun PharmacyItemGrid(pharmacy: List<PharmacyItem>) {
    LazyVerticalGrid(GridCells.Fixed(2)){
        items(pharmacy){item ->
            com.ayberk.IzmirExpress.PharmacyItem(pharmacy = item)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PharmacyItem(pharmacy: PharmacyItem) {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yy")

    val parsedDate = LocalDateTime.parse(pharmacy.Tarih, inputFormatter)
    val formattedDate = parsedDate.format(outputFormatter)

    val customFontFamily = FontFamily(
        Font(R.font.inter)
    )

    val bolgeText = pharmacy.Bolge
    val detailsbolgeText = bolgeText.substringBefore('-').trim()

    val telefonNumarasi = pharmacy.Telefon

    val formattedTelefonNumarasi = String.format(
        "%s %s %s %s %s",
        telefonNumarasi.substring(0, 1),
        telefonNumarasi.substring(1, 4),
        telefonNumarasi.substring(4, 7),
        telefonNumarasi.substring(7, 9),
        telefonNumarasi.substring(9, 11),
        telefonNumarasi.substring(11)
    )

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()

            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(Color.Red)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.pharmacydetails),
                contentDescription = "pharmacy",
                modifier = Modifier
                    .size(80.dp)
                    .padding(start = 16.dp)
            )
            Text(
                text = pharmacy.Adi,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontFamily = customFontFamily,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(start = 4.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = detailsbolgeText,
                textAlign = TextAlign.Start,
                color = Color.Black,
                style = MaterialTheme.typography.labelMedium,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = formattedDate,
                textAlign = TextAlign.End,
                color = Color.White,
                style = MaterialTheme.typography.labelMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
                if (expanded) {
                    Text(
                        text = pharmacy.Adres,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .padding(top = 8.dp)
                    )
                    Text(
                        text = formattedTelefonNumarasi,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Black,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 8.dp),
                    )
                    IconButton(
                        onClick = { expanded = false },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = null,
                            tint = Color.White // Beyaz renkli olacak
                        )
                    }
                } else {
                    IconButton(
                        onClick = { expanded = true },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally) // İkonu dikeyde ortalamak için
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
