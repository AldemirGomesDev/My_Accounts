package br.com.aldemir.data.database.di

import android.content.Context
import br.com.aldemir.data.database.preference.DataStorePreference
import br.com.aldemir.data.database.preference.DataStorePreferenceImpl
import br.com.aldemir.data.database.room.ConfigDatabase
import br.com.aldemir.data.remote.ApiService
import br.com.aldemir.data.remote.RetrofitClient
import okhttp3.Cache
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

private const val DEFAULT_CACHE_SIZE = 10 * 1024 * 1024
const val CACHE_DIR = "NETWORK_CACHE_DIR"

val appModule = module {
    single { ConfigDatabase.createDataBase(androidContext()) }
    factory { get<ConfigDatabase>().expenseDao() }
    factory { get<ConfigDatabase>().recipeDao() }
    factory { get<ConfigDatabase>().monthlyPaymentDao() }
    factory { get<ConfigDatabase>().recipeMonthlyDao() }
    factory { get<ConfigDatabase>().authenticationDao() }
    factory<DataStorePreference> { DataStorePreferenceImpl(context = get()) }

    factory(named(CACHE_DIR)) {
        get<Context>().cacheDir
    }

    single {
        Cache(File(get<File>(named(CACHE_DIR)), "responses"), DEFAULT_CACHE_SIZE.toLong())
    }
    factory<ApiService> { RetrofitClient.provideRetrofit(get()).create(ApiService::class.java) }
}