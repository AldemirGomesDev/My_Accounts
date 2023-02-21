package br.com.aldemir.myaccounts.data.repository.di

import br.com.aldemir.myaccounts.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindExpenseRepository(useCase: ExpenseRepositoryImpl) : ExpenseRepository

    @Binds
    fun bindMonthlyPaymentRepository(useCase: MonthlyPaymentRepositoryImpl) : MonthlyPaymentRepository

    @Binds
    fun bindDataStoreRepository(repository: DataStoreRepositoryImpl) : DataStoreRepository

}