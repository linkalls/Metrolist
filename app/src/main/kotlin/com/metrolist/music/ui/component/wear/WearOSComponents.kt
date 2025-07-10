package com.metrolist.music.ui.component.wear

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.metrolist.music.R
import com.metrolist.music.ui.screens.Screens

/**
 * Wear OS optimized navigation component that replaces the bottom navigation
 * on small screens with a more touch-friendly circular or grid layout
 */
@Composable
fun WearOSNavigation(
    navigationItems: List<Screens>,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    isRound: Boolean = false,
    modifier: Modifier = Modifier
) {
    if (isRound) {
        // Circular navigation for round screens
        CircularNavigation(
            navigationItems = navigationItems,
            currentRoute = currentRoute,
            onNavigate = onNavigate,
            modifier = modifier
        )
    } else {
        // Grid navigation for square screens
        GridNavigation(
            navigationItems = navigationItems,
            currentRoute = currentRoute,
            onNavigate = onNavigate,
            modifier = modifier
        )
    }
}

@Composable
private fun CircularNavigation(
    navigationItems: List<Screens>,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(navigationItems) { screen ->
            val isSelected = currentRoute == screen.route
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onNavigate(screen.route) }
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primaryContainer
                        else Color.Transparent,
                        CircleShape
                    )
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isSelected) screen.iconIdActive else screen.iconIdInactive
                    ),
                    contentDescription = stringResource(screen.titleId),
                    modifier = Modifier.size(24.dp),
                    tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                           else MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = stringResource(screen.titleId),
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                           else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun GridNavigation(
    navigationItems: List<Screens>,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(navigationItems) { screen ->
            val isSelected = currentRoute == screen.route
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable { onNavigate(screen.route) },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                   else MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isSelected) screen.iconIdActive else screen.iconIdInactive
                        ),
                        contentDescription = stringResource(screen.titleId),
                        modifier = Modifier.size(20.dp),
                        tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                               else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = stringResource(screen.titleId),
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                               else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Compact mini player for Wear OS devices
 */
@Composable
fun WearOSMiniPlayer(
    isPlaying: Boolean,
    songTitle: String?,
    artist: String?,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Song info
            if (songTitle != null) {
                Text(
                    text = songTitle,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (artist != null) {
                Text(
                    text = artist,
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
            
            // Control buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onPrevious,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.skip_previous),
                        contentDescription = "Previous",
                        modifier = Modifier.size(20.dp)
                    )
                }
                
                IconButton(
                    onClick = onPlayPause,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            if (isPlaying) R.drawable.pause else R.drawable.play
                        ),
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                IconButton(
                    onClick = onNext,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.skip_next),
                        contentDescription = "Next",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}