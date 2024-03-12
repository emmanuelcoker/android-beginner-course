package com.wema.sportsadaptiveapp.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wema.sportsadaptiveapp.R
import com.wema.sportsadaptiveapp.data.LocalSportProvider
import com.wema.sportsadaptiveapp.models.Sport
import com.wema.sportsadaptiveapp.ui.theme.SportsAdaptiveAppTheme


@Composable
fun SportDetail(
    sport: Sport, modifier: Modifier = Modifier
) {

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        //sport banner
        SportBanner(sport = sport)

        //Sport information
        Column(
            modifier = modifier.padding(vertical = 16.dp, horizontal = 20.dp)
        ) {
            Text(text = stringResource(sport.description), style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun SportBanner(sport: Sport, modifier: Modifier = Modifier) {
    Box {
        Box {
            Image(
                painter = painterResource(sport.banner),
                contentScale = ContentScale.FillWidth,
                contentDescription = stringResource(sport.name)
            )
            Column(
                modifier = modifier
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.scrim
                            ), 0f, 400f
                        )
                    )
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = stringResource(sport.name),
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = pluralStringResource(
                            R.plurals.player_count_caption,
                            count = sport.playerCount,
                            sport.playerCount
                        ),
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = if (sport.isOlympicSport) "Olympic" else "",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SportDetailPreview() {
    SportsAdaptiveAppTheme {
        val sport = LocalSportProvider.defaultSport
        SportDetail(sport = sport)
    }
}