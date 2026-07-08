package com.codandotv.worldcupbestteamsimulator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.github.codandotv.jujubasvg.core.JujubaSVG
import com.github.codandotv.jujubasvg.core.rememberJujubaCommander
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import worldcupbestteamsimulator.shared.generated.resources.Res

@Composable
fun HomeScreen() {
    var svgText by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            svgText = Res.readBytes("files/soccer-field.svg").decodeToString()
        }
    }

    Scaffold {
        Column {
            if (svgText != null) {
                JujubaSVG(
                    svgText = svgText!!,
                    commander = rememberJujubaCommander(),
                    onElementClick = {
                        it.toString()
                    }
                )
            }
        }
    }
}
