package br.com.aldemir.login.di

import br.com.aldemir.login.presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {
    viewModelOf(::LoginViewModel)
}