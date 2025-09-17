package br.com.aldemir.home.presentation.di

import br.com.aldemir.home.presentation.view.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationHomeModule = module {
    viewModelOf(::HomeViewModel)
}