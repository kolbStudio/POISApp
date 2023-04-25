package com.testpois.ui.feature

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.testpois.domain.extensions.onFailure
import com.testpois.domain.extensions.onSuccess
import com.testpois.features.getPois.domain.model.Pois
import com.testpois.domain.usecases.DeletePoiUseCase
import com.testpois.domain.usecases.GetAllPoisUseCase
import com.testpois.ui.common.GEOCODER_RESULTS
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class PoiViewModel @Inject constructor(
    private val getAllPoisUseCase: GetAllPoisUseCase,
    private val deletePoiUseCase: DeletePoiUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PoiUiState())
    val uiState: StateFlow<PoiUiState> = _uiState.asStateFlow()

    private val _showConfirmationDialog = MutableLiveData<Pair<Boolean, Pois?>>()
    val showConfirmationDialog: LiveData<Pair<Boolean, Pois?>> = _showConfirmationDialog

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getPois()
    }

    fun getPois(forceRefresh : Boolean = false) = viewModelScope.launch {
        getAllPoisUseCase
            .execute(forceRefresh)
            .onStart { _uiState.value = _uiState.value.copy(isLoading = true) }
            .onEach { res ->
                res.onFailure { _uiState.value = _uiState.value.copy(error = it) }
                res.onSuccess { _uiState.value = _uiState.value.copy(poisList = it) }
            }
            .onCompletion { _uiState.value = _uiState.value.copy(isLoading = false) }
            .collect()
    }

    fun showConfirmationClick(pois: Pois) {
        _showConfirmationDialog.value = Pair(true, pois)
    }

    fun onDialogDismissDelete() = viewModelScope.launch {
        _showConfirmationDialog.value = Pair(false, null)
    }
    fun onRemovePoi(pois: Pois) = viewModelScope.launch {
        deletePoiUseCase.invoke(pois)
        getPois()
        _showConfirmationDialog.value = Pair(false, null)
    }

    fun openGoogleMaps(address: String, context: Context) {
        val gmmIntentUri = Uri.parse("geo:0,0?q= $address")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        val option : Bundle = Bundle.EMPTY
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(context, mapIntent, option)
    }

    fun getLocation(address: String, context: Context): LatLng {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val location = geocoder.getFromLocationName(address, GEOCODER_RESULTS)?.firstOrNull()
            LatLng(location?.latitude ?: 0.0, location?.longitude ?: 0.0)
        } catch (ex: IOException) {
            LatLng(0.0, 0.0)
        }
    }

}
