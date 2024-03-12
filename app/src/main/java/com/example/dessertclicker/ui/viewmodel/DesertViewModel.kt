package com.example.dessertclicker.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource
import com.example.dessertclicker.ui.DesertUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DesertViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(DesertUiState())
    val uiState: StateFlow<DesertUiState> = _uiState.asStateFlow()


    init {
        initializeDeserts()
    }

    fun initializeDeserts() {
        _uiState.update { desertUiState ->
            desertUiState.copy(deserts = Datasource.dessertList)
        }
    }
    fun getCurrentDesertPrice(): Int {
        return _uiState.value.deserts[_uiState.value.currentDessertIndex].price
    }

    fun getCurrentDesertImage(): Int {
        return _uiState.value.deserts[_uiState.value.currentDessertIndex].imageId
    }

}