package br.com.aldemir.myaccounts.domain.usecase.di

import br.com.aldemir.myaccounts.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindGetAllExpenseUseCase(useCase: GetAllExpenseUseCaseImpl) : GetAllExpenseUseCase

    @Binds
    fun bindDeleteExpenseUseCase(useCase: DeleteExpenseUseCaseImpl) : DeleteExpenseUseCase

    @Binds
    fun bindAddExpenseUseCase(useCase: AddExpenseUseCaseImpl) : AddExpenseUseCase

    @Binds
    fun bindAddMonthlyPaymentUseCase(useCase: AddMonthlyPaymentUseCaseImpl) : AddMonthlyPaymentUseCase

    @Binds
    fun bindGetAllByIdExpenseUseCase(useCase: GetAllByIdExpenseUseCaseImpl) : GetAllByIdExpenseUseCase

    @Binds
    fun bindGetByIdMonthlyPaymentUseCase(useCase: GetByIdMonthlyPaymentUseCaseImpl) : GetByIdMonthlyPaymentUseCase

    @Binds
    fun bindUpdateMonthlyPaymentUseCase(useCase: UpdateMonthlyPaymentUseCaseImpl) : UpdateMonthlyPaymentUseCase

    @Binds
    fun bindGetAllExpensesMonthUseCase(useCase: GetAllExpensesMonthUseCaseImpl) : GetAllExpensesMonthUseCase
}