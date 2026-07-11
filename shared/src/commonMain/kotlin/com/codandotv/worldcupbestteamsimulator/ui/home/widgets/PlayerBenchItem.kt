package com.codandotv.worldcupbestteamsimulator.ui.home.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.codandotv.worldcupbestteamsimulator.ui.home.PlayerRowItem

@Composable
fun PlayerBenchItem(
    item: PlayerRowItem,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(Color.LightGray)
    ) {
        Box {
            AsyncImage(
                item.player.avatarUrl,
                contentDescription = item.player.name,
                modifier = Modifier.size(80.dp)
                    .clip(CircleShape)
                    .align(Alignment.TopCenter),
            )

            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    contentDescription = "check circle"
                )
            }
        }
        Text(
            text = item.player.name,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
    }

}
