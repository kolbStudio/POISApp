package com.testpois.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.MobileFriendly
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.testpois.R
import com.testpois.ui.feature.PoiViewModel
import com.testpois.ui.feature.SpacerHeight
import com.testpois.ui.theme.TextStadium

const val FIFTEEN_F = 15f
const val GEOCODER_RESULTS = 10

@Composable
fun BaseLoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        CircularProgressIndicator(
            color = Color.White,
            strokeWidth = 4.dp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 20.sp,
                color = TextStadium
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = colorResource(id = R.color.primary_color)
        )
    )
}

@Composable
fun BaseSearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = TextStadium, fontSize = 20.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                tint = colorResource(id = R.color.primary_color),
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("")
                    }
                ) {
                    Icon(
                        Icons.Rounded.Close,
                        tint = colorResource(id = R.color.primary_color),
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.colors(
            focusedTextColor = TextStadium,
            cursorColor = TextStadium,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun ConfirmationDeleteDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            icon = { Icon(Icons.Outlined.MobileFriendly, contentDescription = "Yes No Dialog") },
            title = { Text(text = stringResource(id = R.string.dialog_yes_no_title)) },
            text = { Text(text = stringResource(id = R.string.dialog_yes_no_answer)) },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text(stringResource(id = R.string.dialog_yes_no_confirm_button))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(stringResource(id = R.string.dialog_yes_no_dismiss_button))
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    val textState = remember { mutableStateOf(TextFieldValue("Mestalla")) }
    BaseSearchView(textState)
}

@Composable
fun ShowLocation(latLng: LatLng, title: String, viewModel: PoiViewModel, address: String) {
    val properties by remember { mutableStateOf(MapProperties(mapType = MapType.SATELLITE)) }
    val context = LocalContext.current

    Surface(
        shadowElevation = 20.dp,
        tonalElevation = 7.dp,
        shape = ShapeDefaults.Large,
        modifier = Modifier
            .padding(8.dp)
    ) {
        GoogleMap(
            cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(latLng, FIFTEEN_F)
            },
            uiSettings = MapUiSettings(
                scrollGesturesEnabled = false,
                zoomControlsEnabled = false,
                zoomGesturesEnabled = false,
            ),
            properties = properties,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Marker(
                state = MarkerState(position = latLng),
                title = title,
            )
        }
    }
    SpacerHeight(size = 16.dp)

    Row(
        modifier = Modifier
            .background(TextStadium)
            .padding(4.dp)
            .clickable { viewModel.openGoogleMaps(address, context) }) {
        Icon(
            imageVector = Icons.Outlined.Map,
            contentDescription = "Maps",
            tint = colorResource(id = R.color.primary_color)
        )
        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = "Open In Maps",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = colorResource(id = R.color.primary_color)
        )
    }
}
