package com.ayberk.IzmirExpress

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.ayberk.IzmirExpress.model.OnemliyerX
import com.ayberk.IzmirExpress.ui.theme.red
import com.ayberk.IzmirExpress.ui.theme.yellow
import com.ayberk.IzmirExpress.viewmodel.DataViewModel
import com.ayberk.izmirilkyardim.R

@Composable
fun EmergencyassemblyareaScreen(navHostController: NavHostController) {
    EmergencyList(navHostController)
}

@Composable
fun EmergencyList( navHostController: NavHostController,viewModel: DataViewModel = hiltViewModel()
) {
    val emergencyassemblyList by remember { viewModel.emergencyCollectList }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }

    EmergencyItemGrid(emergencyassembly = emergencyassemblyList,navHostController = navHostController)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator(color = yellow)
        }
        if(errorMessage.isNotEmpty()) {
            RetryViewEmergency(error = errorMessage) {
                viewModel.loadEmergencyCollect()
            }
        }
    }
}


@Composable
fun EmergencyItemGrid(navHostController: NavHostController, emergencyassembly: List<OnemliyerX>) {
    LazyVerticalGrid(GridCells.Fixed(2)){
        items(emergencyassembly){item ->
            EmergencyItem(emergencyassembly = item)
        }
    }
}

@Composable
fun EmergencyItem(emergencyassembly: OnemliyerX) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(yellow)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.assemblypoint),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(80.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = emergencyassembly.MAHALLE,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center,
                maxLines = 2
            )
            Text(
                text = emergencyassembly.ILCE,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
                    .padding(bottom = 4.dp)
                    .padding(top = 4.dp),
                textAlign = TextAlign.End,
           )
        }
    }
}


@Composable
fun RetryViewEmergency(
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
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(yellow)
        ) {
            Text(text = "Retry")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Bu sayfanın yüklenmesi biraz zaman alabilir...",
            color = Color.Black
        )
    }
}