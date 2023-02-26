package br.com.aldemir.myaccounts.domain.usecase.di

import br.com.aldemir.myaccounts.domain.usecase.*
import br.com.aldemir.myaccounts.domain.usecase.darkmode.ReadDarkModeStateUseCase
import br.com.aldemir.myaccounts.domain.usecase.darkmode.ReadDarkModeStateUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.darkmode.SaveDarkModeStateUseCase
import br.com.aldemir.myaccounts.domain.usecase.darkmode.SaveDarkModeStateUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.expense.add.AddExpenseUseCase
import br.com.aldemir.myaccounts.domain.usecase.expense.add.AddExpenseUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.expense.add.AddMonthlyPaymentUseCase
import br.com.aldemir.myaccounts.domain.usecase.expense.add.AddMonthlyPaymentUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.expense.delete.DeleteExpenseUseCase
import br.com.aldemir.myaccounts.domain.usecase.expense.delete.DeleteExpenseUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.expense.getexpense.GetAllExpenseUseCase
import br.com.aldemir.myaccounts.domain.usecase.expense.getexpense.GetAllExpenseUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.expense.getexpensemonthly.*
import br.com.aldemir.myaccounts.domain.usecase.expense.getexpensepermonth.GetAllExpensePerMonthUseCase
import br.com.aldemir.myaccounts.domain.usecase.expense.getexpensepermonth.GetAllExpensePerMonthUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.expense.getexpensepermonth.GetAllExpensesMonthUseCase
import br.com.aldemir.myaccounts.domain.usecase.expense.getexpensepermonth.GetAllExpensesMonthUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.expense.update.UpdateMonthlyPaymentUseCase
import br.com.aldemir.myaccounts.domain.usecase.expense.update.UpdateMonthlyPaymentUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindGetAllExpenseUseCase(useCase: GetAllExpenseUseCaseImpl): GetAllExpenseUseCase

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
    fun bindGetAllMonthlyPaymentUseCase(useCase: GetAllMonthlyPaymentUseCaseImpl) : GetAllMonthlyPaymentUseCase

    @Binds
    fun bindUpdateMonthlyPaymentUseCase(useCase: UpdateMonthlyPaymentUseCaseImpl) : UpdateMonthlyPaymentUseCase

    @Binds
    fun bindGetAllExpensesMonthUseCase(useCase: GetAllExpensesMonthUseCaseImpl) : GetAllExpensesMonthUseCase

    @Binds
    fun bindGetAllExpensePerMonthUseCase(useCase: GetAllExpensePerMonthUseCaseImpl) : GetAllExpensePerMonthUseCase

    @Binds
    fun bindSaveDarkModeStateUseCase(useCase: SaveDarkModeStateUseCaseImpl) : SaveDarkModeStateUseCase

    @Binds
    fun bindReadDarkModeStateUseCase(useCase: ReadDarkModeStateUseCaseImpl) : ReadDarkModeStateUseCase
}