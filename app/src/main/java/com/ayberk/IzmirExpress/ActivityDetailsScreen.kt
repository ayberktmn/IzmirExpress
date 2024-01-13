package com.ayberk.IzmirExpress


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.ayberk.IzmirExpress.model.ActivityDetails
import com.ayberk.IzmirExpress.model.ActivitysItem
import com.ayberk.IzmirExpress.model.EtkinlikMerkezi
import com.ayberk.IzmirExpress.model.SeansListesi
import com.ayberk.IzmirExpress.ui.theme.white
import com.ayberk.IzmirExpress.util.Resource
import com.ayberk.IzmirExpress.viewmodel.DataViewModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Whitelist

@Composable
fun ActivityDetailsScreen(navHostController:NavHostController,Id:Int,viewModel: DataViewModel = hiltViewModel()) {

    val detailsId = produceState<Resource<ActivityDetails>>(initialValue = Resource.Loading()){
        value = viewModel.LoadActivityDetails(Id = Id)
    }.value
    detailsId.data?.let { details ->
        DetailsList(activityDetails = details)
    } ?: run {
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
fun DetailsItem(activityDetails: ActivityDetails) {

    val ucrestsizmi = activityDetails.SeansListesi[0].UcretsizMi
    val document: Document = Jsoup.parse(activityDetails.Aciklama)
    val cleanedHtml: String = Jsoup.clean(document.body().html(), Whitelist.none())

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .shadow(8.dp, shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(white)
    ){
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .shadow(8.dp, shape = MaterialTheme.shapes.medium),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(Color.Black),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Image(
                painter = rememberImagePainter(data = activityDetails.KucukAfis),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(200.dp)
                    .padding(top = 2.dp),
                contentScale = ContentScale.FillWidth
            )
            Text(text = activityDetails.Adi,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .shadow(8.dp, shape = MaterialTheme.shapes.medium),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(white),
            border = BorderStroke(1.dp, Color.Gray)
        ){
            Text(text = "Tür: " + activityDetails.Tur, color = Color.Black, textAlign = TextAlign.Center, modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally))

            Text(text = "Etkinlik Merkezi: " + activityDetails.EtkinlikMerkezi.Adi, textAlign = TextAlign.Center, color = Color.Black, modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally))

            if (ucrestsizmi){
                Text(text = "Ücret: " + "Ücretsiz", textAlign = TextAlign.Center, color = Color.Black, modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally))
            }
            else{
                Text(text = "Ücret: " + "Ücretli", textAlign = TextAlign.Center, color = Color.Black, modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally))
            }
            Spacer(modifier = Modifier.padding(bottom = 4.dp))
        }

        activityDetails.SeansListesi.forEach { seans ->

            val context = LocalContext.current

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .shadow(8.dp, shape = MaterialTheme.shapes.medium),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(Color.Blue)
            ){
                Text(text = "Seanslar: "+ seans.BiletSatisAciklama, textAlign = TextAlign.Center, color = Color.White, modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally))

                Text(text = "Doluluk Oranı: "+ seans.DolulukOranı, textAlign = TextAlign.Center, color = Color.White, modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally))

                if (seans.BiletSatisLinki != null){
                    Text(
                        text = buildAnnotatedString {
                        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                            append("Bilet Satış Linki")
                        }
                    },
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            seans.BiletSatisLinki?.let { link ->
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                                context.startActivity(intent)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.padding(bottom = 4.dp))
                }
                else{
                    Text(text = "Bilet Satışı Bulunmamaktadır", textAlign = TextAlign.Center, color = Color.White, modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally))
                }
                Spacer(modifier = Modifier.padding(bottom = 4.dp))
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 8.dp))

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
                .shadow(8.dp, shape = MaterialTheme.shapes.medium),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(Color.White)
        ){
            Text(text = "Açıklama" + cleanedHtml, textAlign = TextAlign.Center, color = Color.Black, modifier = Modifier
                .padding(top = 8.dp)
                .padding(start = 4.dp)
                .padding(end = 4.dp)
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally))
        }
    }
}
