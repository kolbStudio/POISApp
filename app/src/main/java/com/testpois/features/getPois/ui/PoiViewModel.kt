package com.testpois.features.getPois.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testpois.domain.extensions.onFailure
import com.testpois.domain.extensions.onSuccess
import com.testpois.features.getPois.domain.model.Pois
import com.testpois.features.getPois.domain.usecases.DeletePoiUseCase
import com.testpois.features.getPois.domain.usecases.GetAllPoisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _showConfirmationDialog = MutableLiveData<Boolean>()
    val showConfirmationDialog: LiveData<Boolean> = _showConfirmationDialog

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

    fun showConfirmationClick() {
        _showConfirmationDialog.value = true
    }

    fun onDialogDismissDelete() = viewModelScope.launch {
        _showConfirmationDialog.value = false
    }

    fun onRemovePoi(pois: Pois) = viewModelScope.launch {
        deletePoiUseCase.invoke(pois)
        getPois()
        _showConfirmationDialog.value = false
    }

}
