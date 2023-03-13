package com.testpois.features.getPois.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.testpois.features.getPois.domain.model.Pois
import com.testpois.features.getPois.domain.model.PoisData
import com.testpois.ui.common.BaseLoadingScreen
import com.testpois.ui.common.BaseSearchView
import com.testpois.ui.model.Routes
import com.testpois.ui.theme.TextStadium
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

@Composable
fun PoiScreen(uiState: PoiUiState, navController: NavController) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    OnCharge(uiState = uiState, navController, textState)

    if (uiState.isLoading) BaseLoadingScreen()

    uiState.error?.let {
        Log.d("TAG", "PoiScreen: $it")
        BaseLoadingScreen()
    }
}

@Composable
fun OnCharge(
    uiState: PoiUiState,
    navController: NavController,
    state: MutableState<TextFieldValue>
) {

    Column {
        BaseSearchView(state = state)
        PoiList(uiState.poisData, navController, state)
    }
}

@Composable
fun PoiList(poisData: PoisData, navController: NavController, state: MutableState<TextFieldValue>) {
    val titles = getPoiTitles(pois = poisData)
    var filteredPois: List<Pois>
    LazyColumn {
        val searchedText = state.value.text
        filteredPois = if (searchedText.isEmpty()) titles
        else {
            val resultList = ArrayList<Pois>()
            for (title in titles) {
                if (title.title.lowercase(Locale.getDefault())
                        .contains(searchedText.lowercase(Locale.getDefault()))
                ) {
                    resultList.add(title)
                }
            }
            resultList
        }

        items(filteredPois) {
            ItemPoi(pois = it, navController)
        }
    }
}

fun getPoiTitles(pois: PoisData): List<Pois> {
    return pois.list.map { it }
}

@Composable
fun ItemPoi(pois: Pois, navController: NavController) {
    val imagerPainter = rememberAsyncImagePainter(model = pois.image)
    val encodedUrl = URLEncoder.encode(pois.image, StandardCharsets.UTF_8.toString())

    Surface(
        shadowElevation = 20.dp,
        tonalElevation = 7.dp,
        shape = ShapeDefaults.Large,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 32.dp)
            .clickable {
                navController.run {
                    navigate(
                        Routes.PoiDetailScreen
                            .createRoute(pois.title, pois.geoCoordinates, encodedUrl)
                    )
                }
            }
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
                    fontSize = 20.sp,
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
