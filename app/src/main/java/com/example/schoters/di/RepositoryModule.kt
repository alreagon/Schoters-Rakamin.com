package com.example.schoters.di

import com.example.schoters.data.remote.repository.RemoteRepository
import org.koin.dsl.module

val repoModule = module {
    single { RemoteRepository(get()) }
}