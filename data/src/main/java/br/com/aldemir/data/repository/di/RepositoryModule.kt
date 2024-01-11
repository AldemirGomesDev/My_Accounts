package br.com.aldemir.data.repository.di

import br.com.aldemir.data.repository.darktheme.DataStoreRepository
import br.com.aldemir.data.repository.darktheme.DataStoreRepositoryImpl
import br.com.aldemir.data.repository.expense.ExpenseRepository
import br.com.aldemir.data.repository.expense.ExpenseRepositoryImpl
import br.com.aldemir.data.repository.expense.MonthlyPaymentRepository
import br.com.aldemir.data.repository.expense.MonthlyPaymentRepositoryImpl
import br.com.aldemir.data.repository.recipe.RecipeMonthlyRepository
import br.com.aldemir.data.repository.recipe.RecipeMonthlyRepositoryImpl
import br.com.aldemir.data.repository.recipe.RecipeRepository
import br.com.aldemir.data.repository.recipe.RecipeRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    factory<ExpenseRepository> { ExpenseRepositoryImpl(expenseDao = get()) }
    factory<MonthlyPaymentRepository> { MonthlyPaymentRepositoryImpl(monthlyPaymentDao = get()) }
    factory<RecipeRepository> { RecipeRepositoryImpl(recipeDao = get()) }
    factory<RecipeMonthlyRepository> { RecipeMonthlyRepositoryImpl(recipeMonthlyDao = get()) }
    factory<DataStoreRepository> { DataStoreRepositoryImpl(dataStorePreference = get()) }
}
