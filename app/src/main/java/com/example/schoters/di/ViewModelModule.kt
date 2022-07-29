package com.example.schoters.di

import com.example.schoters.ui.viewmodel.NewssViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NewssViewModel(get(),get()) }
}