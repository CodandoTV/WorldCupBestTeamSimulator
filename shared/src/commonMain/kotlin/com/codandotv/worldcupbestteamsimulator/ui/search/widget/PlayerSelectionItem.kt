package com.codandotv.worldcupbestteamsimulator.ui.search.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.codandotv.worldcupbestteamsimulator.domain.model.PlayersByTeamItem

@Composable
fun PlayerSelectionItem(
    playerItem: PlayersByTeamItem.PlayerItem,
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val player = playerItem.player
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelectedChange(!selected) },
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar circle: you can replace this with an image loader on Android
            val initials = remember(player.name) {
                player.name
                    .split(' ')
                    .filter { it.isNotEmpty() }
                    .map { it.first().uppercaseChar() }
                    .take(2)
                    .joinToString("")
            }

            AsyncImage(
                modifier = Modifier.size(48.dp)
                    .align(Alignment.CenterVertically)
                    .clip(CircleShape),
                imageUrl = playerItem.player.avatarUrl,
                contentScale = ContentScale.Crop,
                contentDescription = playerItem.player.name,
                filterQuality = FilterQuality.None,
                onFailure = {
                    Text(
                        text = initials,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )
                }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${player.countryName} • ${player.position} • #${player.jerseyNumber}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Checkbox(
                checked = selected,
                onCheckedChange = onSelectedChange
            )
        }
    }
}