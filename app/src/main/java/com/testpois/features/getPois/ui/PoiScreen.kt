package com.testpois.features.getPois.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.testpois.features.getPois.domain.model.Pois
import com.testpois.features.getPois.domain.model.PoisData
import com.testpois.ui.common.BaseLoadingScreen
import com.testpois.ui.theme.TextStadium

@Composable
fun PoiScreen(uiState: PoiUiState) {

    OnCharge(uiState = uiState)

    if (uiState.isLoading) BaseLoadingScreen()

    uiState.error?.let {
        Log.d("TAG", "PoiScreen: $it")
        BaseLoadingScreen()
    }

}

@Composable
fun OnCharge(uiState: PoiUiState) {

    Box(modifier = Modifier.fillMaxSize()) {
        PoiList(uiState.poisData)
    }
}

@Composable
fun PoiList(poisData: PoisData) {
    LazyColumn {
        items(poisData.list) {
            ItemPoi(pois = it)
        }
    }
}

@Composable
fun ItemPoi(pois: Pois) {
    val imagerPainter = rememberAsyncImagePainter(model = pois.image)

    Surface(
        shadowElevation = 20.dp,
        tonalElevation = 7.dp,
        shape = ShapeDefaults.Large,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 32.dp)
    ) {
        Column() {
            Surface(
                shadowElevation = 20.dp,
                tonalElevation = 7.dp,
                shape = ShapeDefaults.Large,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = imagerPainter,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.FillBounds
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Text(
                    text = "" + pois.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = TextStadium
                )

                SpacerHeight(size = 16.dp)

                Text(
                    text = "Location in: " + pois.geoCoordinates,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = TextStadium
                )

                SpacerHeight(size = 16.dp)
            }
        }
    }

}

@Composable
fun SpacerHeight(size: Dp) {
    Spacer(modifier = Modifier.height(size))
}
