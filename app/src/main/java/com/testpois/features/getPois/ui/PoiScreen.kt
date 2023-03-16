package com.testpois.features.getPois.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.testpois.R
import com.testpois.features.getPois.domain.model.Pois
import com.testpois.features.getPois.domain.model.PoisData
import com.testpois.ui.common.BaseLoadingScreen
import com.testpois.ui.common.BaseSearchView
import com.testpois.ui.common.ConfirmationDeleteDialog
import com.testpois.ui.model.Routes
import com.testpois.ui.theme.TextStadium
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

@Composable
fun PoiScreen(uiState: PoiUiState, navController: NavController, poiViewModel: PoiViewModel) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    OnCharge(uiState = uiState, navController, textState, poiViewModel)

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
    state: MutableState<TextFieldValue>,
    poiViewModel: PoiViewModel
) {

    Column {
        BaseSearchView(state = state)
        PoiList(uiState.poisList, navController, state, poiViewModel)
    }
}

@Composable
fun PoiList(
    poisList: List<Pois>,
    navController: NavController,
    state: MutableState<TextFieldValue>,
    poiViewModel: PoiViewModel
) {
    val isLoading by poiViewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    var filteredPois: List<Pois>
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { poiViewModel.getPois(true) },
        indicator = { stateSwipe, refreshTrigger ->
            SwipeRefreshIndicator(
                state = stateSwipe,
                refreshTriggerDistance = refreshTrigger,
                backgroundColor = colorResource(id = R.color.primary_color),
                contentColor = TextStadium
            )
        }
    ) {

        LazyColumn() {
            val searchedText = state.value.text
            filteredPois = if (searchedText.isEmpty()) poisList
            else {
                val resultList = ArrayList<Pois>()
                for (title in poisList) {
                    if (title.title.lowercase(Locale.getDefault())
                            .contains(searchedText.lowercase(Locale.getDefault()))
                    ) {
                        resultList.add(title)
                    }
                }
                resultList
            }

            items(filteredPois) {
                ItemPoi(pois = it, navController, poiViewModel)
            }
        }
    }

}

@Composable
fun ItemPoi(pois: Pois, navController: NavController, poiViewModel: PoiViewModel) {
    val showConfirmationDialog by poiViewModel.showConfirmationDialog.observeAsState(false)
    val imagerPainter = rememberAsyncImagePainter(model = pois.image)
    val encodedUrl = URLEncoder.encode(pois.image, StandardCharsets.UTF_8.toString())

    Surface(
        shadowElevation = 20.dp,
        tonalElevation = 7.dp,
        shape = ShapeDefaults.Large,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        poiViewModel.showConfirmationClick()
                    }
                )
            }
    ) {
        Column(modifier = Modifier.background(colorResource(id = R.color.primary_color))) {
            Surface(
                shadowElevation = 20.dp,
                tonalElevation = 7.dp,
                shape = ShapeDefaults.Large,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Image(
                    painter = imagerPainter,
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clickable {
                            navController.run {
                                navigate(
                                    Routes.PoiDetailScreen
                                        .createRoute(pois.title, pois.geoCoordinates, encodedUrl)
                                )
                            }
                        },
                    contentScale = ContentScale.FillBounds
                )
            }

            ConfirmationDeleteDialog(
                show = showConfirmationDialog,
                onDismiss = { poiViewModel.onDialogDismissDelete() },
                onConfirm = { poiViewModel.onRemovePoi(pois) }
            )

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
