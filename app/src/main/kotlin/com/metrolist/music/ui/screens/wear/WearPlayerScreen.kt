package com.metrolist.music.ui.screens.wear

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaMetadata
import coil.compose.AsyncImage
import com.metrolist.music.R
import com.metrolist.music.LocalPlayerConnection
import com.metrolist.music.ui.component.wear.WearContentBox
import com.metrolist.music.utils.wear.isRoundScreen

/**
 * Simplified player interface optimized for Wear OS devices
 */
@Composable
fun WearPlayerScreen(
    modifier: Modifier = Modifier
) {
    val playerConnection = LocalPlayerConnection.current
    val isPlaying by playerConnection?.isPlaying?.collectAsState() ?: remember { mutableStateOf(false) }
    val mediaMetadata by playerConnection?.mediaMetadata?.collectAsState() ?: remember { mutableStateOf<MediaMetadata?>(null) }
    val isRound = isRoundScreen()
    
    WearContentBox(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        if (isRound) {
            CircularPlayerLayout(
                isPlaying = isPlaying,
                mediaMetadata = mediaMetadata,
                onPlayPause = { playerConnection?.player?.let { 
                    if (it.isPlaying) it.pause() else it.play() 
                }},
                onNext = { playerConnection?.player?.seekToNextMediaItem() },
                onPrevious = { playerConnection?.player?.seekToPreviousMediaItem() }
            )
        } else {
            SquarePlayerLayout(
                isPlaying = isPlaying,
                mediaMetadata = mediaMetadata,
                onPlayPause = { playerConnection?.player?.let { 
                    if (it.isPlaying) it.pause() else it.play() 
                }},
                onNext = { playerConnection?.player?.seekToNextMediaItem() },
                onPrevious = { playerConnection?.player?.seekToPreviousMediaItem() }
            )
        }
    }
}

@Composable
private fun CircularPlayerLayout(
    isPlaying: Boolean,
    mediaMetadata: MediaMetadata?,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Album art (smaller for round screens)
        if (mediaMetadata?.artworkUri != null) {
            AsyncImage(
                model = mediaMetadata.artworkUri,
                contentDescription = "Album artwork",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.album),
                    contentDescription = "No artwork",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        
        // Song title
        Text(
            text = mediaMetadata?.title?.toString() ?: "No title",
            fontSize = 12.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        // Artist
        Text(
            text = mediaMetadata?.artist?.toString() ?: "Unknown artist",
            fontSize = 10.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        
        // Control buttons in circular arrangement
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onPrevious,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.skip_previous),
                    contentDescription = "Previous",
                    modifier = Modifier.size(18.dp)
                )
            }
            
            IconButton(
                onClick = onPlayPause,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(
                        if (isPlaying) R.drawable.pause else R.drawable.play
                    ),
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = MaterialTheme.colorScheme.onPrimary,
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
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun SquarePlayerLayout(
    isPlaying: Boolean,
    mediaMetadata: MediaMetadata?,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Album art
        if (mediaMetadata?.artworkUri != null) {
            AsyncImage(
                model = mediaMetadata.artworkUri,
                contentDescription = "Album artwork",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.album),
                    contentDescription = "No artwork",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        
        // Song info
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = mediaMetadata?.title?.toString() ?: "No title",
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                text = mediaMetadata?.artist?.toString() ?: "Unknown artist",
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        
        // Control buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onPrevious,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.skip_previous),
                    contentDescription = "Previous",
                    modifier = Modifier.size(20.dp)
                )
            }
            
            IconButton(
                onClick = onPlayPause,
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(
                        if (isPlaying) R.drawable.pause else R.drawable.play
                    ),
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(28.dp)
                )
            }
            
            IconButton(
                onClick = onNext,
                modifier = Modifier.size(36.dp)
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