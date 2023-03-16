package com.testpois.features.getPois.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.testpois.R
import com.testpois.ui.theme.TextStadium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoiDetailScreen(
    navController: NavController,
    title: String?,
    geocoordinates: String?,
    image: String?
) {
    Scaffold(
        topBar = { TopAppBarDetail(navController) },
        containerColor = TextStadium
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            PoiDetail(title, geocoordinates, image)
        }
    }
}

@Composable
fun PoiDetail(
    title: String?,
    geocoordinates: String?,
    image: String?
) {
    val imagerPainter = rememberAsyncImagePainter(image)
    Surface(
        shadowElevation = 20.dp,
        tonalElevation = 7.dp,
        shape = ShapeDefaults.Large,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.background(colorResource(id = R.color.primary_color))) {
            Surface(
                shadowElevation = 20.dp,
                tonalElevation = 7.dp,
                shape = ShapeDefaults.Large,
                modifier = Modifier.padding(8.dp)
            ) {
                Image(
                    painter = imagerPainter,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
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
                    text = "" + title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = TextStadium
                )

                SpacerHeight(size = 16.dp)

                Text(
                    text = "Location in: $geocoordinates",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextStadium
                )

                SpacerHeight(size = 16.dp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDetail(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.text_back),
                fontSize = 18.sp,
                color = TextStadium,
                modifier = Modifier.clickable { navController.popBackStack() }
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Button",
                modifier = Modifier.clickable { navController.popBackStack() }
            )
        }
    )
}
