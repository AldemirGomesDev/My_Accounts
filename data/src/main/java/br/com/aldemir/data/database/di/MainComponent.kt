package br.com.aldemir.data.database.di

import br.com.aldemir.data.database.preference.DataStorePreference
import br.com.aldemir.data.database.preference.DataStorePreferenceImpl
import br.com.aldemir.data.database.room.ConfigDatabase
import br.com.aldemir.data.remote.ApiService
import br.com.aldemir.data.remote.ApiServiceImpl
import br.com.aldemir.data.remote.ResponseCacheControlInterceptor
import br.com.aldemir.data.remote.MyAccountHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { ConfigDatabase.createDataBase(androidContext()) }
    factory { get<ConfigDatabase>().expenseDao() }
    factory { get<ConfigDatabase>().recipeDao() }
    factory { get<ConfigDatabase>().monthlyPaymentDao() }
    factory { get<ConfigDatabase>().recipeMonthlyDao() }
    factory { get<ConfigDatabase>().authenticationDao() }
    factory<DataStorePreference> { DataStorePreferenceImpl(context = get()) }

    single {
        ResponseCacheControlInterceptor()
    }
    single {
        MyAccountHttpClient.provideHttpClient(interceptor = get())
    }

    factory<ApiService> { ApiServiceImpl(httpClient = get()) }
}