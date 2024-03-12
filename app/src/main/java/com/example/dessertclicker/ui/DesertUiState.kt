package com.example.dessertclicker.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.dessertclicker.model.Dessert

data class DesertUiState(
    val revenue: Int = 0,
    val desertsSold: Int = 0,
    val currentDessertIndex: Int = 0,
    val deserts: List<Dessert> = listOf()
)

