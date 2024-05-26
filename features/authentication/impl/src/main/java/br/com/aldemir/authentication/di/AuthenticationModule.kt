package br.com.aldemir.authentication.di

import br.com.aldemir.authentication.data.BiometricHelper
import br.com.aldemir.authentication.data.BiometricHelperImpl
import br.com.aldemir.authentication.data.CryptoManager
import br.com.aldemir.authentication.data.CryptoManagerImpl
import br.com.aldemir.authentication.presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authenticationModule = module {
    viewModelOf(::LoginViewModel)
    factoryOf(::CryptoManagerImpl) bind CryptoManager::class
    factoryOf(::BiometricHelperImpl) bind BiometricHelper::class
}