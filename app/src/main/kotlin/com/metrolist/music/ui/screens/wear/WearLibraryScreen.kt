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
import com.metrolist.music.ui.component.wear.WearColumn
import com.metrolist.music.utils.wear.isRoundScreen

/**
 * Simplified library interface for Wear OS devices
 */
@Composable
fun WearLibraryScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val isRound = isRoundScreen()
    
    WearColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Library title
        Text(
            text = stringResource(R.string.filter_library),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = if (isRound) 14.sp else 16.sp
        )
        
        // Library sections
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(getWearLibrarySections()) { section ->
                WearLibrarySectionCard(
                    section = section,
                    onClick = { 
                        navController.navigate(section.route)
                    },
                    isRound = isRound
                )
            }
        }
    }
}

@Composable
private fun WearLibrarySectionCard(
    section: WearLibrarySection,
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
                painter = painterResource(section.iconRes),
                contentDescription = section.title,
                modifier = Modifier.size(if (isRound) 20.dp else 24.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = section.title,
                    fontSize = if (isRound) 12.sp else 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                if (section.subtitle.isNotEmpty()) {
                    Text(
                        text = section.subtitle,
                        fontSize = if (isRound) 9.sp else 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }
            
            Icon(
                painter = painterResource(R.drawable.arrow_forward),
                contentDescription = null,
                modifier = Modifier.size(if (isRound) 16.dp else 18.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }
    }
}

data class WearLibrarySection(
    val title: String,
    val subtitle: String = "",
    val iconRes: Int,
    val route: String
)

@Composable
private fun getWearLibrarySections(): List<WearLibrarySection> {
    return listOf(
        WearLibrarySection(
            title = "Downloaded",
            subtitle = "Offline music",
            iconRes = R.drawable.offline,
            route = "library/downloaded"
        ),
        WearLibrarySection(
            title = "Songs",
            subtitle = "All tracks",
            iconRes = R.drawable.queue_music,
            route = "library/songs"
        ),
        WearLibrarySection(
            title = "Albums",
            subtitle = "Collections",
            iconRes = R.drawable.album,
            route = "library/albums"
        ),
        WearLibrarySection(
            title = "Artists",
            subtitle = "Performers",
            iconRes = R.drawable.person,
            route = "library/artists"
        ),
        WearLibrarySection(
            title = "Playlists",
            subtitle = "Your mixes",
            iconRes = R.drawable.playlist_play,
            route = "library/playlists"
        ),
        WearLibrarySection(
            title = "History",
            subtitle = "Recently played",
            iconRes = R.drawable.history,
            route = "history"
        )
    )
}