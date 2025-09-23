package br.com.aldemir.myaccounts

import br.com.aldemir.myaccounts.di.mainModules
import org.koin.core.context.startKoin

class KoinIosHelper {
    fun initKoin() {
        startKoin {
             modules(mainModules)
        }
    }
}