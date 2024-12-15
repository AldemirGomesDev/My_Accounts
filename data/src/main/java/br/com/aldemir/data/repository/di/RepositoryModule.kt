package br.com.aldemir.data.repository.di

import br.com.aldemir.data.repository.authentication.AuthenticationRepositoryImpl
import br.com.aldemir.domain.repository.DataStoreRepository
import br.com.aldemir.data.repository.darktheme.DataStoreRepositoryImpl
import br.com.aldemir.domain.repository.ExpenseRepository
import br.com.aldemir.data.repository.expense.ExpenseRepositoryImpl
import br.com.aldemir.domain.repository.MonthlyPaymentRepository
import br.com.aldemir.data.repository.expense.MonthlyPaymentRepositoryImpl
import br.com.aldemir.domain.repository.RecipeMonthlyRepository
import br.com.aldemir.data.repository.recipe.RecipeMonthlyRepositoryImpl
import br.com.aldemir.domain.repository.RecipeRepository
import br.com.aldemir.data.repository.recipe.RecipeRepositoryImpl
import br.com.aldemir.domain.repository.AuthenticationRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    factoryOf(::AuthenticationRepositoryImpl) bind AuthenticationRepository::class
    factory<ExpenseRepository> { ExpenseRepositoryImpl(expenseDao = get()) }
    factory<MonthlyPaymentRepository> { MonthlyPaymentRepositoryImpl(monthlyPaymentDao = get()) }
    factory<RecipeRepository> { RecipeRepositoryImpl(recipeDao = get()) }
    factory<RecipeMonthlyRepository> { RecipeMonthlyRepositoryImpl(recipeMonthlyDao = get()) }
    factory<DataStoreRepository> { DataStoreRepositoryImpl(dataStorePreference = get()) }
}
