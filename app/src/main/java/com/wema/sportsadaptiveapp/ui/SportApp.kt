package com.wema.sportsadaptiveapp.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wema.sportsadaptiveapp.R
import com.wema.sportsadaptiveapp.models.Sport
import com.wema.sportsadaptiveapp.ui.theme.SportsAdaptiveAppTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SportsApp(
    sportViewModel: SportViewModel = viewModel(),
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val sportUiState by sportViewModel.uiState.collectAsState()
    val activity = LocalContext.current as Activity
    val density = LocalDensity.current
    val animationTime = 300

    BackHandler {
        if (sportUiState.isShowingDetail) {
            sportViewModel.backToHome()
        } else {
            activity.finish()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (sportUiState.isShowingDetail) {
                        IconButton(onClick = {
                            sportViewModel.backToHome()
                        }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                        }
                    }
                },
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            )
        }
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = true, // Always visible
            enter = slideInHorizontally(
                initialOffsetX = { -300 }, // small slide 300px
                animationSpec = tween(
                    durationMillis = animationTime,
                    easing = LinearEasing // interpolator
                )
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { -300 },
                animationSpec = tween(
                    durationMillis = animationTime,
                    easing = LinearEasing
                )
            )
        ) {
            Column(
                modifier = modifier.padding(it)
            ) {
                when (windowSize) {
                    WindowWidthSizeClass.Compact,
                    WindowWidthSizeClass.Medium -> {
                        if (!sportUiState.isShowingDetail) {
                            SportList(
                                sportViewModel = sportViewModel,
                                sports = sportUiState.sports,
                                modifier = modifier.padding(it)
                            )
                        } else {
                            sportUiState.currentlyShowingSport?.let { SportDetail(sport = it) }
                        }
                    }

                    WindowWidthSizeClass.Expanded -> {
                        Row(modifier = modifier.fillMaxWidth()) {

                            Column(modifier = modifier.weight(1f)) {
                                SportList(
                                    sports = sportUiState.sports,
                                    sportViewModel = sportViewModel
                                )
                            }

                            AnimatedVisibility(
                                modifier = Modifier.fillMaxSize(),
                                visible = sportUiState.isShowingDetail, // Always visible
                                enter = slideInHorizontally(
                                    initialOffsetX = { -300 }, // small slide 300px
                                    animationSpec = tween(
                                        durationMillis = animationTime,
                                        easing = LinearEasing // interpolator
                                    )
                                ),
                                exit = slideOutHorizontally(
                                    targetOffsetX = { -300 },
                                    animationSpec = tween(
                                        durationMillis = animationTime,
                                        easing = LinearEasing
                                    )
                                )
                            ) {
                                Column(
                                    modifier = modifier
                                        .weight(2f)
                                        .animateEnterExit(
                                            enter = slideInHorizontally {
                                                with(density) { -300.dp.roundToPx() }
                                            },
                                            exit = slideOutHorizontally() + shrinkHorizontally() + fadeOut()
                                        )
                                ) {
                                    if (sportUiState.currentlyShowingSport != null) {

                                        // This content will slide in and out
                                        sportUiState.currentlyShowingSport?.let { SportDetail(sport = it) }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SportList(sports: List<Sport>, modifier: Modifier = Modifier, sportViewModel: SportViewModel) {

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sports) { sport ->
            SportItem(sport = sport, sportViewModel = sportViewModel)
        }

    }
}


@Preview(showBackground = true, widthDp = 900)
@Composable
fun SportsAppPreview() {
    SportsAdaptiveAppTheme {
        SportsApp(windowSize = WindowWidthSizeClass.Expanded)
    }
}