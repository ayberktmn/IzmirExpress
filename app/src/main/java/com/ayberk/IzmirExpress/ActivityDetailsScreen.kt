package com.ayberk.IzmirExpress

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayberk.IzmirExpress.model.ActivityDetails
import com.ayberk.IzmirExpress.model.ActivitysItem
import com.ayberk.IzmirExpress.model.EtkinlikMerkezi
import com.ayberk.IzmirExpress.model.SeansListesi
import com.ayberk.IzmirExpress.util.Resource
import com.ayberk.IzmirExpress.viewmodel.DataViewModel

@Composable
fun ActivityDetailsScreen(navHostController:NavHostController,Id:Int,viewModel: DataViewModel = hiltViewModel()) {

    val detailsId = produceState<Resource<ActivityDetails>>(initialValue = Resource.Loading()){
        value = viewModel.LoadActivityDetails(Id = Id)
    }.value

    println("gelen id ${Id}")

    detailsId.data?.let { details ->
        DetailsList(activityDetails = details)
    } ?: run {
        // Handle the case where detailsId.data is null or loading state
        // For example, you can display a loading indicator or an error message.
        RetryView(error = "Error Details") {
            
        }
    }

}

@Composable
fun DetailsList(activityDetails: ActivityDetails) {
    ItemGrid(activityDetails)
}

@Composable
fun ItemGrid(activityDetails: ActivityDetails) {
    LazyVerticalGrid(GridCells.Fixed(1)) {
        items(listOf(activityDetails)) {
            DetailsItem(activityDetails = it)
        }
    }
}

@Composable
fun DetailsItem(activityDetails:ActivityDetails) {

    Card(
        modifier = Modifier
            .size(300.dp, 150.dp)
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Text(text = activityDetails.Tur, color = Color.White)

        Text(text = activityDetails.EtkinlikMerkezi.Adi, color = Color.White)
    }
}