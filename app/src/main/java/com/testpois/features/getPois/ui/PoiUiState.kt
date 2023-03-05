package com.testpois.features.getPois.ui

import com.testpois.domain.extensions.DomainError
import com.testpois.features.getPois.domain.model.PoisData

data class PoiUiState (
    val isLoading : Boolean = false,
    val poisData: PoisData = PoisData(
        list = emptyList()
    ),
    val error: DomainError? = null
)
