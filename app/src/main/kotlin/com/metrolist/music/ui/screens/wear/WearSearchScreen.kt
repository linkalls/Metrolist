package com.metrolist.music.ui.screens.wear

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.metrolist.music.R
import com.metrolist.music.ui.component.wear.WearColumn
import com.metrolist.music.utils.wear.isRoundScreen

/**
 * Simplified search interface for Wear OS devices
 */
@Composable
fun WearSearchScreen(
    navController: NavController,
    initialQuery: String = "",
    modifier: Modifier = Modifier
) {
    val isRound = isRoundScreen()
    var searchQuery by rememberSaveable { mutableStateOf(initialQuery) }
    val keyboardController = LocalSoftwareKeyboardController.current
    
    WearColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Search input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { 
                Text(
                    text = stringResource(R.string.search_yt_music),
                    fontSize = if (isRound) 10.sp else 12.sp
                ) 
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = null,
                    modifier = Modifier.size(if (isRound) 16.dp else 20.dp)
                )
            },
            trailingIcon = if (searchQuery.isNotEmpty()) {
                {
                    IconButton(
                        onClick = { searchQuery = "" },
                        modifier = Modifier.size(if (isRound) 24.dp else 32.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.close),
                            contentDescription = "Clear",
                            modifier = Modifier.size(if (isRound) 16.dp else 20.dp)
                        )
                    }
                }
            } else null,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (searchQuery.isNotEmpty()) {
                        keyboardController?.hide()
                        navController.navigate("search/${java.net.URLEncoder.encode(searchQuery, "UTF-8")}")
                    }
                }
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(if (isRound) 20.dp else 12.dp),
            textStyle = LocalTextStyle.current.copy(
                fontSize = if (isRound) 11.sp else 13.sp
            ),
            singleLine = true
        )
        
        // Search suggestions/quick actions
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(getWearSearchSuggestions()) { suggestion ->
                WearSearchSuggestionCard(
                    suggestion = suggestion,
                    onClick = { 
                        searchQuery = suggestion.query
                        navController.navigate("search/${java.net.URLEncoder.encode(suggestion.query, "UTF-8")}")
                    },
                    isRound = isRound
                )
            }
        }
        
        // Back button
        if (isRound) {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(40.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }
        } else {
            Button(
                onClick = { navController.navigateUp() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Back",
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun WearSearchSuggestionCard(
    suggestion: WearSearchSuggestion,
    onClick: () -> Unit,
    isRound: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(if (isRound) 44.dp else 48.dp),
        shape = RoundedCornerShape(if (isRound) 22.dp else 12.dp),
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
                painter = painterResource(suggestion.iconRes),
                contentDescription = suggestion.query,
                modifier = Modifier.size(if (isRound) 18.dp else 20.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = suggestion.query,
                fontSize = if (isRound) 11.sp else 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

data class WearSearchSuggestion(
    val query: String,
    val iconRes: Int
)

@Composable
private fun getWearSearchSuggestions(): List<WearSearchSuggestion> {
    return listOf(
        WearSearchSuggestion("Popular music", R.drawable.trending_up),
        WearSearchSuggestion("My Library", R.drawable.library_music),
        WearSearchSuggestion("Rock", R.drawable.album),
        WearSearchSuggestion("Pop", R.drawable.album),
        WearSearchSuggestion("Jazz", R.drawable.album),
        WearSearchSuggestion("Playlist", R.drawable.queue_music)
    )
}