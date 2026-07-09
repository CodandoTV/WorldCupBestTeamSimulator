package com.codandotv.worldcupbestteamsimulator.domain

import org.koin.dsl.module

val domainModule = module {
    factory { GetPlayersByTeamItemsUseCase(get()) }
}
