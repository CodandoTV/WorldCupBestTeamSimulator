package com.codandotv.worldcupbestteamsimulator.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.codandotv.worldcupbestteamsimulator.LocalNavController
import com.codandotv.worldcupbestteamsimulator.SEARCH_ROUTE
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

    val navigator = LocalNavController.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            FloatingActionButton(
                onClick = {
                    navigator.navigate(SEARCH_ROUTE)
                },
                content = {
                    Icon(
                        imageVector = Icons.Filled.PlusOne,
                        contentDescription = "add players"
                    )
                }
            )
        }
    ) {
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
