package br.com.aldemir.myaccounts

import br.com.aldemir.authentication.di.authenticationModule
import br.com.aldemir.common.PlatformContext
import br.com.aldemir.data.repository.di.dataModule
import br.com.aldemir.domain.di.domainModule
import br.com.aldemir.expense.di.expenseModule
import br.com.aldemir.home.presentation.di.presentationHomeModule
import br.com.aldemir.myaccounts.di.mainModule
import br.com.aldemir.recipe.di.recipeModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(appModules: List<Module> = emptyList()): KoinApplication {
    return startKoin {
        modules(
            arrayListOf<Module>().apply {
                add(mainModule)
                add(dataModule)
                add(domainModule)
                add(authenticationModule)
                add(presentationHomeModule)
                add(expenseModule)
                add(recipeModule)
            } + appModules
        )
    }
}