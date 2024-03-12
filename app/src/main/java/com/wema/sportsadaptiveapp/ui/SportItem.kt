package com.wema.sportsadaptiveapp.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wema.sportsadaptiveapp.R
import com.wema.sportsadaptiveapp.data.LocalSportProvider
import com.wema.sportsadaptiveapp.models.Sport
import com.wema.sportsadaptiveapp.ui.theme.SportsAdaptiveAppTheme

@Composable
fun SportItem(sport: Sport, modifier: Modifier = Modifier, sportViewModel: SportViewModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)))
            .background(Color.Transparent)
            .clickable {
                sportViewModel.updateShowingSportDetail(sport)
            }
    ) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier.height(dimensionResource(R.dimen.card_image_height))
            ) {
                //image
                SportImage(image = sport.image, description = sport.name)

                //sport details
                SportInfo(sport = sport)
            }
        }
    }
}

@Composable
fun SportImage(
    @DrawableRes image: Int,
    @StringRes description: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(image),
        contentDescription = stringResource(description),
        modifier = modifier.size(
            dimensionResource(R.dimen.card_image_height)
        )
    )
}


@Composable
fun SportInfo(sport: Sport, modifier: Modifier = Modifier){
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxHeight()
            .padding(12.dp)
    ) {
        Column {
            Text(
                text = stringResource(sport.name),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(sport.description),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

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
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = if (sport.isOlympicSport) "Olympic" else "",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}


@Preview(showBackground = false)
@Composable
fun SportItemPreview() {
    val sportViewModel: SportViewModel = SportViewModel()
    SportsAdaptiveAppTheme {
        val sport = LocalSportProvider.defaultSport
        SportItem(sport = sport, sportViewModel = sportViewModel)
    }
}