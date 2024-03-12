package com.wema.sportsadaptiveapp.data

import com.wema.sportsadaptiveapp.R
import com.wema.sportsadaptiveapp.models.Sport

object LocalSportProvider {
    val defaultSport: Sport = getSportList()[0]

    fun getSportList(): List<Sport> {
        return listOf(
           Sport(
               id = 1,
               name = R.string.baseball,
               description = R.string.sport_detail_text,
               banner = R.drawable.ic_baseball_banner,
               image = R.drawable.ic_baseball_square,
               playerCount =  9,
               isOlympicSport = true
           ),

            Sport(
                id = 2,
                name = R.string.badminton,
                description = R.string.sport_detail_text,
                banner = R.drawable.ic_badminton_banner,
                image = R.drawable.ic_badminton_square,
                playerCount =  1,
                isOlympicSport = true
            ),
            Sport(
                id = 3,
                name = R.string.basketball,
                description = R.string.sport_detail_text,
                banner = R.drawable.ic_basketball_banner,
                image = R.drawable.ic_basketball_square,
                playerCount =  5,
                isOlympicSport = true
            ),
            Sport(
                id = 4,
                name = R.string.bowling,
                description = R.string.sport_detail_text,
                banner = R.drawable.ic_bowling_banner,
                image = R.drawable.ic_bowling_square,
                playerCount =  1
            ),
            Sport(
                id = 5,
                name = R.string.cycling,
                description = R.string.sport_detail_text,
                banner = R.drawable.ic_cycling_banner,
                image = R.drawable.ic_cycling_square,
                playerCount = 1,
                isOlympicSport = true
            ),
            Sport(
                id = 6,
                name = R.string.golf,
                playerCount = 1,
                image = R.drawable.ic_golf_square,
                banner = R.drawable.ic_golf_banner,
                description = R.string.sport_detail_text
            ),

            Sport(
                id = 7,
                name = R.string.running,
                playerCount = 1,
                image = R.drawable.ic_running_square,
                banner = R.drawable.ic_running_banner,
                description = R.string.sport_detail_text,
                isOlympicSport = true
            ),
            Sport(
                id = 8,
                name = R.string.soccer,
                playerCount = 11,
                image = R.drawable.ic_soccer_square,
                banner = R.drawable.ic_soccer_banner,
                description = R.string.sport_detail_text,
                isOlympicSport = true
            ),

            Sport(
                id = 9,
                name = R.string.swimming,
                playerCount = 1,
                image = R.drawable.ic_swimming_square,
                banner = R.drawable.ic_swimming_banner,
                description = R.string.sport_detail_text,
                isOlympicSport = true
            ),

            Sport(
                id = 10,
                name = R.string.table_tennis,
                playerCount = 1,
                image = R.drawable.ic_table_tennis_square,
                banner = R.drawable.ic_table_tennis_banner,
                description = R.string.sport_detail_text,
                isOlympicSport = true
            ),

            Sport(
                id = 11,
                name = R.string.tennis,
                playerCount = 1,
                image = R.drawable.ic_tennis_square,
                banner = R.drawable.ic_tennis_banner,
                description = R.string.sport_detail_text,
                isOlympicSport = true
            ),
        )
    }
}