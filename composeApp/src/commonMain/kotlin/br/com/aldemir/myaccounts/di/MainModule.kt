package br.com.aldemir.myaccounts.di

import br.com.aldemir.authentication.di.authenticationModule
import br.com.aldemir.data.di.dataBaseModule
import br.com.aldemir.data.repository.di.dataModule
import br.com.aldemir.domain.di.domainModule
import br.com.aldemir.expense.di.expenseModule
import br.com.aldemir.home.presentation.di.presentationHomeModule
import br.com.aldemir.myaccounts.MainViewModel
import br.com.aldemir.recipe.di.recipeModule
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    viewModel { MainViewModel(get(), get(), get(), get()) }
}

val mainModules  = listOf(
    dataBaseModule,
    dataModule,
    domainModule,
    authenticationModule,
    presentationHomeModule,
    expenseModule,
    recipeModule,
    mainModule
)