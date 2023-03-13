package com.testpois

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.testpois.features.getPois.ui.PoiViewModel
import com.testpois.ui.common.BaseTopBar
import com.testpois.ui.navigation.AppNavigation
import com.testpois.ui.theme.TextStadium
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val poiViewModel: PoiViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                topBar = { BaseTopBar() },
                containerColor = TextStadium
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    AppNavigation(viewModel = poiViewModel)
                }
            }
        }
    }

}
