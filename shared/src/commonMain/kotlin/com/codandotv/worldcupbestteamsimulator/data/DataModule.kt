package com.codandotv.worldcupbestteamsimulator.data

import org.koin.dsl.module

val dataModule = module {
    single<WorldCupRepository> { WorldCupRepositoryImpl() }
}
