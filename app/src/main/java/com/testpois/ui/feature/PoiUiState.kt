package com.testpois.ui.feature

import com.testpois.domain.extensions.DomainError
import com.testpois.features.getPois.domain.model.Pois

data class PoiUiState (
    val isLoading : Boolean = false,
    val poisList: List<Pois> =  emptyList(),
    val error: DomainError? = null
)
