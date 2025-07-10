package com.metrolist.music.ui.screens.wear

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.metrolist.music.R
import com.metrolist.music.ui.component.wear.WearOSNavigation
import com.metrolist.music.ui.component.wear.WearColumn
import com.metrolist.music.ui.screens.Screens
import com.metrolist.music.utils.wear.isRoundScreen

/**
 * Simplified home screen for Wear OS devices with essential quick actions
 */
@Composable
fun WearHomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val isRound = isRoundScreen()
    
    WearColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // App title
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = if (isRound) 16.sp else 18.sp
        )
        
        // Quick actions
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(getWearQuickActions()) { action ->
                WearQuickActionCard(
                    action = action,
                    onClick = { 
                        when (action.route) {
                            "player" -> {
                                // Navigate to player or expand current playing
                                // This would be handled by the bottom sheet player
                            }
                            else -> navController.navigate(action.route)
                        }
                    },
                    isRound = isRound
                )
            }
        }
        
        // Navigation
        WearOSNavigation(
            navigationItems = listOf(
                Screens.Home,
                Screens.Search,
                Screens.Library
            ),
            currentRoute = Screens.Home.route,
            onNavigate = { route ->
                if (route == Screens.Search.route) {
                    navController.navigate("search/")
                } else {
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            isRound = isRound,
            modifier = Modifier.height(if (isRound) 60.dp else 80.dp)
        )
    }
}

@Composable
private fun WearQuickActionCard(
    action: WearQuickAction,
    onClick: () -> Unit,
    isRound: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(if (isRound) 48.dp else 52.dp),
        shape = RoundedCornerShape(if (isRound) 24.dp else 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(action.iconRes),
                contentDescription = action.title,
                modifier = Modifier.size(if (isRound) 20.dp else 24.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = action.title,
                fontSize = if (isRound) 12.sp else 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

data class WearQuickAction(
    val title: String,
    val iconRes: Int,
    val route: String
)

@Composable
private fun getWearQuickActions(): List<WearQuickAction> {
    return listOf(
        WearQuickAction(
            title = stringResource(R.string.search),
            iconRes = R.drawable.search,
            route = "search/"
        ),
        WearQuickAction(
            title = stringResource(R.string.filter_library),
            iconRes = R.drawable.library_music,
            route = Screens.Library.route
        ),
        WearQuickAction(
            title = "Player",
            iconRes = R.drawable.queue_music,
            route = "player"
        ),
        WearQuickAction(
            title = stringResource(R.string.history),
            iconRes = R.drawable.history,
            route = "history"
        )
    )
}