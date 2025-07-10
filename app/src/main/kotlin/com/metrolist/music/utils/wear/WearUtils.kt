package com.metrolist.music.utils.wear

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

/**
 * Utilities for Wear OS device detection and screen configuration
 */
object WearUtils {
    
    /**
     * Checks if the device is a Wear OS device
     */
    fun isWearDevice(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_WATCH)
    }
    
    /**
     * Checks if the screen is round (typical for many Wear OS devices)
     */
    fun isRoundScreen(context: Context): Boolean {
        return context.resources.configuration.isScreenRound
    }
    
    /**
     * Gets the appropriate screen edge padding based on screen shape
     */
    fun getScreenEdgePadding(context: Context): Int {
        return if (isRoundScreen(context)) {
            context.resources.getDimensionPixelSize(
                context.resources.getIdentifier("wear_screen_edge_padding", "dimen", context.packageName)
            )
        } else {
            context.resources.getDimensionPixelSize(
                context.resources.getIdentifier("wear_screen_edge_padding", "dimen", context.packageName)
            )
        }
    }
}

/**
 * Composable function to check if we're on a Wear OS device
 */
@Composable
fun isWearDevice(): Boolean {
    val context = LocalContext.current
    return remember { WearUtils.isWearDevice(context) }
}

/**
 * Composable function to check if we have a round screen
 */
@Composable
fun isRoundScreen(): Boolean {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    return remember(configuration) { WearUtils.isRoundScreen(context) }
}

/**
 * Composable function to get appropriate screen dimensions for Wear OS
 */
@Composable
fun getWearScreenSize(): Pair<Int, Int> {
    val configuration = LocalConfiguration.current
    return Pair(configuration.screenWidthDp, configuration.screenHeightDp)
}