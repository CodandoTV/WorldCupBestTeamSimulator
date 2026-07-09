package com.codandotv.worldcupbestteamsimulator.data

import com.codandotv.worldcupbestteamsimulator.domain.WorldCupRepository
import org.koin.dsl.module

val dataModule = module {
    single<WorldCupRepository> { WorldCupRepositoryImpl() }
}
