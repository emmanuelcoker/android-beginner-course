package com.wema.sportsadaptiveapp.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Sport(
    val id: Int,
    @StringRes val name: Int,
    @StringRes val description: Int,
    val playerCount: Int,
    var isOlympicSport: Boolean = false,
    @DrawableRes val banner: Int,
    @DrawableRes val image: Int,
)
