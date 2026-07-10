package com.codandotv.worldcupbestteamsimulator.ui.home

import androidx.lifecycle.ViewModel
import com.codandotv.worldcupbestteamsimulator.domain.WorldCupRepository
import com.codandotv.worldcupbestteamsimulator.domain.model.Player
import com.github.codandotv.jujubasvg.core.commander.Command
import com.github.codandotv.jujubasvg.model.NodeCoordinate

private const val SOCCER_FIELD_ID = "soccer_field"

private const val FIELD_WIDTH = 572f
private const val AVATAR_SIZE = 60
private const val PADDING_X = 70f

private val POSITION_Y_MAP = mapOf(
    "goalkeeper" to 900f,
    "defender" to 660f,
    "midfielder" to 420f,
    "forward" to 180f,
)

class HomeViewModel(
    private val repository: WorldCupRepository
) : ViewModel() {

    fun selectedPlayersDrawingCommands(): List<Command> {
        val players = repository.selectedPlayers()
        val grouped = players.groupBy { it.position.lowercase() }

        return POSITION_Y_MAP.flatMap { (position, y) ->
            positionCommands(grouped[position] ?: emptyList(), y)
        }
    }

    private fun positionCommands(players: List<Player>, y: Float): List<Command> {
        val usableWidth = FIELD_WIDTH - 2 * PADDING_X
        return players.mapIndexed { index, player ->
            val x = if (players.size == 1) {
                FIELD_WIDTH / 2
            } else {
                PADDING_X + (usableWidth / (players.size + 1)) * (index + 1)
            }
            Command.AddRoundedImage(
                imageId = convertToId(player),
                coordinate = NodeCoordinate(x = x, y = y),
                imageUrl = player.avatarUrl,
                elementId = SOCCER_FIELD_ID,
                widthInPx = AVATAR_SIZE,
                heightInPx = AVATAR_SIZE,
            )
        }
    }

    private fun convertToId(player: Player): String {
        val name = player.name.replace(' ', '_')
        val countryName = player.countryName.replace(' ', '_')
        val jerseyNumber = player.jerseyNumber.toString()
        return "$name-$countryName-$jerseyNumber"
    }
}