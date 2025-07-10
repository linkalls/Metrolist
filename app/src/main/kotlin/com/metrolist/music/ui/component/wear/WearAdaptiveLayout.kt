package com.metrolist.music.ui.component.wear

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.metrolist.music.utils.wear.isRoundScreen
import kotlin.math.*

/**
 * Provides appropriate padding for round Wear OS screens to avoid content being cut off
 * at the edges of circular displays
 */
@Composable
fun Modifier.wearScreenPadding(): Modifier {
    val isRound = isRoundScreen()
    val configuration = LocalConfiguration.current
    
    return if (isRound) {
        val screenSize = min(configuration.screenWidthDp, configuration.screenHeightDp)
        val edgePadding = calculateRoundScreenPadding(screenSize)
        this.padding(horizontal = edgePadding, vertical = (edgePadding * 0.7f).dp)
    } else {
        this.padding(12.dp)
    }
}

/**
 * Calculates the appropriate padding for round screens based on screen size
 */
private fun calculateRoundScreenPadding(screenSizeDp: Int): Dp {
    return when {
        screenSizeDp <= 280 -> 20.dp  // Small round screens (like Galaxy Watch 4 40mm)
        screenSizeDp <= 320 -> 24.dp  // Medium round screens
        else -> 28.dp                 // Larger round screens
    }
}

/**
 * Layout that adapts to round vs square Wear OS screens
 */
@Composable
fun WearAdaptiveLayout(
    roundContent: @Composable () -> Unit,
    squareContent: @Composable () -> Unit
) {
    if (isRoundScreen()) {
        roundContent()
    } else {
        squareContent()
    }
}

/**
 * Provides content padding that ensures content doesn't get cut off on round screens
 */
@Composable
fun WearContentBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier.wearScreenPadding(),
        content = content
    )
}

/**
 * Column that automatically applies appropriate padding for Wear OS screens
 */
@Composable
fun WearColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier.wearScreenPadding(),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = content
    )
}

/**
 * Row that automatically applies appropriate padding for Wear OS screens
 */
@Composable
fun WearRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier.wearScreenPadding(),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        content = content
    )
}