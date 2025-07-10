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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.metrolist.music.R
import com.metrolist.music.ui.component.wear.WearContentBox
import com.metrolist.music.utils.wear.isRoundScreen
import com.metrolist.music.utils.wear.isWearDevice

/**
 * Demo screen to showcase Wear OS adaptations
 */
@Composable
fun WearDemoScreen(
    modifier: Modifier = Modifier
) {
    val isWear = isWearDevice()
    val isRound = isRoundScreen()
    val configuration = LocalConfiguration.current
    
    WearContentBox(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Demo header
            Text(
                text = "Wear OS Demo",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            
            // Device info card
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Device Info",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Text(
                        text = "Is Wear OS: ${if (isWear) "Yes" else "No"}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Text(
                        text = "Screen Shape: ${if (isRound) "Round" else "Square"}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Text(
                        text = "Screen Size: ${configuration.screenWidthDp}×${configuration.screenHeightDp}dp",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            // Visual demonstration
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Circular button for round screens
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            MaterialTheme.colorScheme.secondary,
                            if (isRound) CircleShape else RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.play),
                        contentDescription = "Play",
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                // Adaptive text size
                Text(
                    text = "Adaptive UI",
                    fontSize = if (isRound) 11.sp else 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            // Feature indicators
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(120.dp)
            ) {
                item {
                    FeatureIndicator(
                        title = "Touch-friendly Navigation",
                        enabled = isWear,
                        isRound = isRound
                    )
                }
                item {
                    FeatureIndicator(
                        title = "Circular Edge Padding",
                        enabled = isRound,
                        isRound = isRound
                    )
                }
                item {
                    FeatureIndicator(
                        title = "Adaptive Typography",
                        enabled = isWear,
                        isRound = isRound
                    )
                }
                item {
                    FeatureIndicator(
                        title = "Optimized Layout",
                        enabled = isWear,
                        isRound = isRound
                    )
                }
            }
        }
    }
}

@Composable
private fun FeatureIndicator(
    title: String,
    enabled: Boolean,
    isRound: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(if (isRound) 16.dp else 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (enabled) 
                MaterialTheme.colorScheme.tertiaryContainer
            else 
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(
                    if (enabled) R.drawable.check else R.drawable.close
                ),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = if (enabled) 
                    MaterialTheme.colorScheme.onTertiaryContainer
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = title,
                fontSize = if (isRound) 10.sp else 12.sp,
                color = if (enabled) 
                    MaterialTheme.colorScheme.onTertiaryContainer
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}