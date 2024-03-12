package com.wema.sportsadaptiveapp.ui

import com.wema.sportsadaptiveapp.models.Sport
import com.wema.sportsadaptiveapp.util.SportContentType

data class SportUiState(
    var currentlyShowingSport: Sport? = null,
    var contentDisplay: SportContentType = SportContentType.LIST,
    var sports: List<Sport> = emptyList(),
    var isShowingDetail: Boolean = false
)