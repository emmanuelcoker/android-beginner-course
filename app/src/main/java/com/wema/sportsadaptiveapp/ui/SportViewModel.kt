package com.wema.sportsadaptiveapp.ui

import androidx.lifecycle.ViewModel
import com.wema.sportsadaptiveapp.data.LocalSportProvider
import com.wema.sportsadaptiveapp.models.Sport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SportViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SportUiState())
    val uiState: StateFlow<SportUiState> = _uiState.asStateFlow()


    init {
        initializeData()
    }

    private fun initializeData() {
        _uiState.update {
            it.copy(
                sports = LocalSportProvider.getSportList(),
                currentlyShowingSport = LocalSportProvider.defaultSport
            )
        }
    }

    fun updateShowingSportDetail(sport: Sport) {
        _uiState.update {
            it.copy(currentlyShowingSport = sport, isShowingDetail = true)
        }
    }

    fun backToHome() {
        _uiState.update {
            it.copy(isShowingDetail = false)
        }
    }


}