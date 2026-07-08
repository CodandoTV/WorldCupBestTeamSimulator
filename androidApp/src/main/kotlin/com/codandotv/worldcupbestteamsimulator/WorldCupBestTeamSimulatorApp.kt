package com.codandotv.worldcupbestteamsimulator

import android.app.Application
import org.koin.android.ext.koin.androidContext

class WorldCupBestTeamSimulatorApp : Application() {

    override fun onCreate() {
        super.onCreate()

        worldCupBestTeamSimulator {
            androidContext(this@WorldCupBestTeamSimulatorApp)
        }
    }
}
