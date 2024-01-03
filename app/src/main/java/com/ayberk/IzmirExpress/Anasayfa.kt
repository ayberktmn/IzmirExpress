package com.ayberk.IzmirExpress

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.ayberk.izmirilkyardim.R
import androidx.navigation.NavHostController

@Composable
fun Anasayfa(navHostController: NavHostController) {
    var allowBackNavigation by remember { mutableStateOf(true) }
    BackHandler(enabled = allowBackNavigation){}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeText()
        HomeItem(navHostController)
    }
}

@Composable
fun HomeText() {
    val customFontFamily = FontFamily(
        Font(R.font.inter)
    )
    Text(
        text = "Anasayfa",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineLarge,
        fontFamily = customFontFamily,
    )
}

@Composable
fun HomeItem(navHostController: NavHostController) {
    Column {
        HomeItemRow(
            imageResId = R.drawable.museum,
            title = "Müzeler", navHostController = navHostController
        )
        HomeItemRow(
            imageResId = R.drawable.pharmacy,
            title = "Nöbetçi Eczaneler", navHostController = navHostController
        )
        HomeItemRow(
            imageResId = R.drawable.watershortage,
            title = "Su Kesintisi", navHostController = navHostController
        )
        HomeItemRow(
            imageResId = R.drawable.assemblypoint,
            title = "Acil Toplanma Alanları", navHostController = navHostController
        )
        HomeItemRow(
            imageResId = R.drawable.activity,
            title = "Etkinlikler", navHostController = navHostController
        )
    }
}

@Composable
fun HomeItemRow(navHostController: NavHostController,imageResId: Int, title: String) {
    Spacer(modifier = Modifier.height(32.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                handleCardClick(navHostController, title)
            },
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.width(64.dp))
        Card(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(8.dp))
                .align(Alignment.CenterVertically)
                .height(IntrinsicSize.Min),
            colors = CardDefaults.cardColors(containerColor = Color.Red)
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontFamily = FontFamily.SansSerif,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

fun handleCardClick(navHostController:NavHostController,title: String) {
    // Tıklanan kartın içeriğine göre özel işlemleri gerçekleştirin
    when (title) {
        "Müzeler" -> {
            // Müze kartına tıklandığında yapılacak işlemler
            navHostController.navigate("muzeler")
        }
        "Nöbetçi Eczaneler" -> {
            // Nöbetçi Eczane kartına tıklandığında yapılacak işlemler
        }
        "Su Kesintisi" -> {
            // Su Kesintisi kartına tıklandığında yapılacak işlemler
        }
        "Acil Toplanma Alanları" -> {
            // Acil Toplanma Alanları kartına tıklandığında yapılacak işlemler
        }
        "Etkinlikler" -> {
            // Etkinlikler kartına tıklandığında yapılacak işlemler
        }
        // Diğer kartlar için gerekli işlemleri ekleyin
    }
}