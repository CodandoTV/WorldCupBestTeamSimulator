package com.codandotv.worldcupbestteamsimulator.data

import com.codandotv.worldcupbestteamsimulator.domain.WorldCupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val dataModule = module {
    single<WorldCupRepository> { WorldCupRepositoryImpl(ioDispatcher = Dispatchers.IO) }
}
