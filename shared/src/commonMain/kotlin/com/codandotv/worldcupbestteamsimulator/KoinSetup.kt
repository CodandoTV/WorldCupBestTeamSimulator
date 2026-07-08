package com.codandotv.worldcupbestteamsimulator

import com.codandotv.worldcupbestteamsimulator.data.dataModule
import com.codandotv.worldcupbestteamsimulator.ui.uiModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun worldCupBestTeamSimulator(platformBlock: KoinApplication.() -> Unit): KoinApplication {
    return startKoin {
        platformBlock()

        modules(uiModule, dataModule)
    }
}
