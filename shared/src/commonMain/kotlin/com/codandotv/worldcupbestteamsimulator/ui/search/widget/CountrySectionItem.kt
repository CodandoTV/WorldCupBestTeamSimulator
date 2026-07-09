package com.codandotv.worldcupbestteamsimulator.ui.search.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.codandotv.worldcupbestteamsimulator.domain.model.PlayersByTeamItem

@Composable
fun CountrySectionItem(
    countryItem: PlayersByTeamItem.TeamItem,
    modifier: Modifier = Modifier
) {
    val countryName = countryItem.countryName
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = countryName,
            style = MaterialTheme.typography.headlineLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}