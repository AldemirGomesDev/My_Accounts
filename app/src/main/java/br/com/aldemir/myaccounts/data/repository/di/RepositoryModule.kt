package br.com.aldemir.myaccounts.data.repository.di

import br.com.aldemir.myaccounts.data.repository.ExpenseRepository
import br.com.aldemir.myaccounts.data.repository.ExpenseRepositoryImpl
import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepositoryImpl
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

}