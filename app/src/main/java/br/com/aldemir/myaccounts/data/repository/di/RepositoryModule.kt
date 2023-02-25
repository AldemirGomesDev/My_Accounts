package br.com.aldemir.myaccounts.data.repository.di

import br.com.aldemir.myaccounts.data.repository.*
import br.com.aldemir.myaccounts.data.repository.darktheme.DataStoreRepository
import br.com.aldemir.myaccounts.data.repository.darktheme.DataStoreRepositoryImpl
import br.com.aldemir.myaccounts.data.repository.expense.ExpenseRepository
import br.com.aldemir.myaccounts.data.repository.expense.ExpenseRepositoryImpl
import br.com.aldemir.myaccounts.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.repository.expense.MonthlyPaymentRepositoryImpl
import br.com.aldemir.myaccounts.data.repository.recipe.RecipeMonthlyRepository
import br.com.aldemir.myaccounts.data.repository.recipe.RecipeMonthlyRepositoryImpl
import br.com.aldemir.myaccounts.data.repository.recipe.RecipeRepository
import br.com.aldemir.myaccounts.data.repository.recipe.RecipeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindExpenseRepository(repository: ExpenseRepositoryImpl) : ExpenseRepository

    @Binds
    fun bindMonthlyPaymentRepository(repository: MonthlyPaymentRepositoryImpl) : MonthlyPaymentRepository

    @Binds
    fun bindDataStoreRepository(repository: DataStoreRepositoryImpl) : DataStoreRepository

    @Binds
    fun bindRecipeRepository(repository: RecipeRepositoryImpl) : RecipeRepository

    @Binds
    fun bindRecipeMonthlyRepository(repository: RecipeMonthlyRepositoryImpl) : RecipeMonthlyRepository

}