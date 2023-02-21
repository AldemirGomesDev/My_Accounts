package br.com.aldemir.myaccounts.data.database.di


import br.com.aldemir.myaccounts.data.database.preference.DataStorePreference
import br.com.aldemir.myaccounts.data.database.preference.DataStorePreferenceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DataStoreModule {
    @Binds
    fun bindDataStorePreference(dataStore: DataStorePreferenceImpl) : DataStorePreference
}