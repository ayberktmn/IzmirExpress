package com.ayberk.IzmirExpress

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayberk.IzmirExpress.model.Activitys
import com.ayberk.IzmirExpress.model.ActivitysItem
import com.ayberk.IzmirExpress.model.Onemliyer
import com.ayberk.IzmirExpress.ui.theme.blue
import com.ayberk.IzmirExpress.viewmodel.DataViewModel

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

    Text(text = activitys.Adi)

}