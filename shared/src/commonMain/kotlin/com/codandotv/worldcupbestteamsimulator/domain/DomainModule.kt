package com.codandotv.worldcupbestteamsimulator.domain

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val domainModule = module {
    factory { GetPlayersByTeamItemsUseCase(repository = get(), defaultDispatcher = Dispatchers.Default) }
}
