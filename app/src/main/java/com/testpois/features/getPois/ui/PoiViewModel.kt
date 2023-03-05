package com.testpois.features.getPois.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testpois.domain.extensions.onFailure
import com.testpois.domain.extensions.onSuccess
import com.testpois.features.getPois.domain.usecases.GetPoisUseCase
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
class PoiViewModel @Inject constructor(private val getPoisUseCase: GetPoisUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(PoiUiState())
    val uiState: StateFlow<PoiUiState> = _uiState.asStateFlow()

    init { getPois() }

    fun getPois() = viewModelScope.launch {
        getPoisUseCase
            .execute(Unit)
            .onStart { _uiState.value = _uiState.value.copy(isLoading = true) }
            .onEach { res ->
                res.onFailure { _uiState.value = _uiState.value.copy(error = it) }
                res.onSuccess { _uiState.value = _uiState.value.copy(poisData = it) }
            }
            .onCompletion { _uiState.value = _uiState.value.copy(isLoading = false) }
            .collect()
    }

}
