package br.com.aldemir.myaccounts

import android.app.Application
import br.com.aldemir.common.PlatformContext
import br.com.aldemir.myaccounts.di.mainModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                mainModules
            )
            PlatformContext.init(this@MyApplication)
        }
    }
}