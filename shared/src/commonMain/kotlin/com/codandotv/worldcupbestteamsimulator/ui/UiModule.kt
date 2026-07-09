package com.codandotv.worldcupbestteamsimulator.ui

import com.codandotv.worldcupbestteamsimulator.ui.search.SearchPlayersViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        HomeViewModel(
            repository = get()
        )
    }

    viewModel {
        SearchPlayersViewModel(
            get()
        )
    }
}
