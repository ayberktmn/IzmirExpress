package com.ayberk.IzmirExpress

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.ayberk.IzmirExpress.model.PharmacyItem
import com.ayberk.IzmirExpress.ui.theme.blue
import com.ayberk.IzmirExpress.viewmodel.DataViewModel
import com.ayberk.izmirilkyardim.R

@Composable
fun Pharmacy(navHostController: NavHostController) {
    PharmacyList()
}

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



@Composable
fun PharmacyItemGrid(pharmacy: List<PharmacyItem>) {
    LazyVerticalGrid(GridCells.Fixed(2)){
        items(pharmacy){item ->
            com.ayberk.IzmirExpress.PharmacyItem(pharmacy = item)
        }
    }
}

@Composable
fun PharmacyItem(pharmacy: PharmacyItem) {
    Card(
        modifier = Modifier
            .size(100.dp, 100.dp)
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
                painter = painterResource(id = R.drawable.pharmacy),
                contentDescription ="pharmacy",
                modifier = Modifier
                    .size(80.dp)
                    .padding(start = 16.dp)
            )
            Text(
                text = pharmacy.Adi,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(start = 4.dp)
            )
        }
    }
}
