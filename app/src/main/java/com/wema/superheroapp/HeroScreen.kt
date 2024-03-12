package com.wema.superheroapp

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wema.superheroapp.model.Hero
import com.wema.superheroapp.ui.theme.Shapes

@Composable
fun HeroesList(
    heroes: List<Hero>,
    modifier: Modifier = Modifier) {
    var visible by remember { mutableStateOf(true) }
   AnimatedVisibility(
       visible = visible,
       enter = slideInHorizontally (
           animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
           )
   ) {
       LazyColumn(
           modifier = modifier
       ) {
           items(heroes) { hero ->
               HeroCard(hero = hero, modifier = Modifier)
           }
       }
   }
}

/**
 * Composable to display the Hero Card with image and description
 *
 * @param hero Hero object that contains the information on the hero
 */
@Composable
fun HeroCard(hero: Hero, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(dimensionResource(R.dimen.padding_small))
            .fillMaxWidth(),
        shape = Shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment =  Alignment.CenterVertically,
            modifier = modifier.padding(16.dp)
        ) {
            HeroInformation(
                name = hero.nameRes,
                description = hero.descriptionRes,
                modifier = Modifier.weight(3f)
            )

            Spacer(modifier = modifier.width(dimensionResource(R.dimen.padding_medium)))

            HeroImage(
                hero.imageRes,
                modifier = modifier.weight(1f)
            )
        }
    }
}


/**
 * Compasable to handle displaying the hero information
 *
 * @param name the hero name string resource Int
 * @param description the hero superpower description string resource Int
 */
@Composable
fun HeroInformation(@StringRes name: Int, @StringRes description: Int, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = stringResource(name),
            style = MaterialTheme.typography.displaySmall
        )

        Text(
            text = stringResource(description),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


/**
 * Composable to handle displaying hero picture
 *
 * @param image the Hero picture as drawable resource
 */
@Composable
fun HeroImage(@DrawableRes image: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(72.dp)
    ) {
        Image(
            modifier = modifier
                .clip(shape = Shapes.small)
                .fillMaxSize(),
            painter = painterResource(image),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

