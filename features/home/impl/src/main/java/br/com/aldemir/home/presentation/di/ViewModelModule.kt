package br.com.aldemir.home.presentation.di

import br.com.aldemir.home.presentation.view.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val presentationHomeModule = module {
    viewModelOf(::HomeViewModel)
}