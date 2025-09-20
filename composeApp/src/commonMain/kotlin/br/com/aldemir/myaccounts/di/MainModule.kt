package br.com.aldemir.myaccounts.di

import br.com.aldemir.myaccounts.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    viewModelOf(::MainViewModel)
}