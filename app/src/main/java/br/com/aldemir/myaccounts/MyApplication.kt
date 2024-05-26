package br.com.aldemir.myaccounts

import android.app.Application
import br.com.aldemir.data.repository.di.dataModule
import br.com.aldemir.expense.di.expenseModule
import br.com.aldemir.domain.di.domainModule
import br.com.aldemir.home.presentation.di.presentationHomeModule
import br.com.aldemir.data.database.di.appModule
import br.com.aldemir.authentication.di.authenticationModule
import br.com.aldemir.myaccounts.di.mainModule
import br.com.aldemir.recipe.di.recipeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module


class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                arrayListOf<Module>().apply {
                    add(appModule)
                    add(mainModule)
                    add(dataModule)
                    add(domainModule)
                    add(presentationHomeModule)
                    add(expenseModule)
                    add(recipeModule)
                    add(authenticationModule)
                }
            )
        }
    }
}