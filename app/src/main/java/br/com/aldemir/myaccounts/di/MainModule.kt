package br.com.aldemir.myaccounts.di

import br.com.aldemir.myaccounts.presentation.shared.view.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    viewModelOf(::MainViewModel)
}