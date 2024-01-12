package br.com.aldemir.domain.di

import br.com.aldemir.domain.usecase.recipe.AddRecipeMonthlyUseCase
import br.com.aldemir.domain.usecase.recipe.AddRecipeUseCase
import br.com.aldemir.domain.usecase.recipe.DeleteRecipeUseCase
import br.com.aldemir.domain.usecase.recipe.GetAllByIdRecipeUseCase
import br.com.aldemir.domain.usecase.recipe.GetAllRecipeMonthlyUseCase
import br.com.aldemir.domain.usecase.recipe.GetAllRecipePerMonthUseCase
import br.com.aldemir.domain.usecase.recipe.GetAllRecipeUseCase
import br.com.aldemir.domain.usecase.recipe.GetByIdRecipeMonthlyUseCase
import br.com.aldemir.domain.usecase.recipe.UpdateRecipeMonthlyUseCase
import br.com.aldemir.domain.usecase.recipe.UpdateRecipeNameAndDescriptionUseCase
import br.com.aldemir.domain.usecase.darkmode.ReadDarkModeStateUseCase
import br.com.aldemir.domain.usecase.darkmode.ReadDarkModeStateUseCaseImpl
import br.com.aldemir.domain.usecase.darkmode.SaveDarkModeStateUseCase
import br.com.aldemir.domain.usecase.darkmode.SaveDarkModeStateUseCaseImpl
import br.com.aldemir.domain.usecase.expense.AddExpenseUseCase
import br.com.aldemir.domain.usecase.expense.AddExpenseUseCaseImpl
import br.com.aldemir.domain.usecase.expense.AddMonthlyPaymentUseCase
import br.com.aldemir.domain.usecase.expense.AddMonthlyPaymentUseCaseImpl
import br.com.aldemir.domain.usecase.expense.DeleteExpenseUseCase
import br.com.aldemir.domain.usecase.expense.DeleteExpenseUseCaseImpl
import br.com.aldemir.domain.usecase.expense.GetAllExpenseUseCase
import br.com.aldemir.domain.usecase.expense.GetAllExpenseUseCaseImpl
import br.com.aldemir.domain.usecase.expense.GetAllByIdExpenseUseCase
import br.com.aldemir.domain.usecase.expense.GetAllByIdExpenseUseCaseImpl
import br.com.aldemir.domain.usecase.expense.GetAllMonthlyPaymentUseCase
import br.com.aldemir.domain.usecase.expense.GetAllMonthlyPaymentUseCaseImpl
import br.com.aldemir.domain.usecase.expense.GetByIdMonthlyPaymentUseCase
import br.com.aldemir.domain.usecase.expense.GetByIdMonthlyPaymentUseCaseImpl
import br.com.aldemir.domain.usecase.expense.GetAllExpensePerMonthUseCase
import br.com.aldemir.domain.usecase.expense.GetAllExpensePerMonthUseCaseImpl
import br.com.aldemir.domain.usecase.expense.GetAllExpensesMonthUseCase
import br.com.aldemir.domain.usecase.expense.GetAllExpensesMonthUseCaseImpl
import br.com.aldemir.domain.usecase.expense.UpdateMonthlyPaymentUseCase
import br.com.aldemir.domain.usecase.expense.UpdateMonthlyPaymentUseCaseImpl
import br.com.aldemir.domain.usecase.recipe.AddRecipeMonthlyUseCaseImpl
import br.com.aldemir.domain.usecase.recipe.AddRecipeUseCaseImpl
import br.com.aldemir.domain.usecase.recipe.DeleteRecipeUseCaseImpl
import br.com.aldemir.domain.usecase.recipe.GetAllRecipeUseCaseImpl
import br.com.aldemir.domain.usecase.recipe.GetAllByIdRecipeUseCaseImpl
import br.com.aldemir.domain.usecase.recipe.GetAllRecipeMonthlyUseCaseImpl
import br.com.aldemir.domain.usecase.recipe.GetByIdRecipeMonthlyUseCaseImpl
import br.com.aldemir.domain.usecase.recipe.GetAllRecipeMonthUseCase
import br.com.aldemir.domain.usecase.recipe.GetAllRecipeMonthUseCaseImpl
import br.com.aldemir.domain.usecase.recipe.GetAllRecipePerMonthUseCaseImpl
import br.com.aldemir.domain.usecase.recipe.UpdateRecipeMonthlyUseCaseImpl
import br.com.aldemir.domain.usecase.recipe.UpdateRecipeNameAndDescriptionUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::AddExpenseUseCaseImpl) { bind<AddExpenseUseCase>() }
    factoryOf(::GetAllExpenseUseCaseImpl) { bind<GetAllExpenseUseCase>() }
    factoryOf(::DeleteExpenseUseCaseImpl) { bind<DeleteExpenseUseCase>() }
    factory<AddMonthlyPaymentUseCase> { AddMonthlyPaymentUseCaseImpl(monthlyPaymentRepository = get()) }
    factory<GetAllByIdExpenseUseCase> { GetAllByIdExpenseUseCaseImpl(monthlyPaymentRepository = get()) }
    factory<GetByIdMonthlyPaymentUseCase> { GetByIdMonthlyPaymentUseCaseImpl(monthlyPaymentRepository = get()) }
    factory<GetAllMonthlyPaymentUseCase> { GetAllMonthlyPaymentUseCaseImpl(monthlyPaymentRepository = get()) }
    factory<UpdateMonthlyPaymentUseCase> { UpdateMonthlyPaymentUseCaseImpl(monthlyPaymentRepository = get()) }
    factory<GetAllExpensesMonthUseCase> { GetAllExpensesMonthUseCaseImpl(monthlyPaymentRepository = get()) }
    factory<GetAllExpensePerMonthUseCase> { GetAllExpensePerMonthUseCaseImpl(monthlyPaymentRepository = get()) }
    factory<SaveDarkModeStateUseCase> { SaveDarkModeStateUseCaseImpl(dataStoreRepository = get()) }
    factory<ReadDarkModeStateUseCase> { ReadDarkModeStateUseCaseImpl(dataStoreRepository = get()) }

    factory<AddRecipeUseCase> { AddRecipeUseCaseImpl(recipeRepository = get()) }
    factory<AddRecipeMonthlyUseCase> { AddRecipeMonthlyUseCaseImpl(recipeMonthlyRepository = get()) }
    factory<GetAllRecipeUseCase> { GetAllRecipeUseCaseImpl(recipeRepository = get()) }
    factory<GetAllRecipePerMonthUseCase> { GetAllRecipePerMonthUseCaseImpl(recipePerMonthlyRepository = get()) }
    factory<GetAllRecipeMonthlyUseCase> { GetAllRecipeMonthlyUseCaseImpl(recipeMonthlyRepository = get()) }
    factory<GetAllByIdRecipeUseCase> { GetAllByIdRecipeUseCaseImpl(recipeMonthlyRepository = get()) }
    factory<UpdateRecipeMonthlyUseCase> { UpdateRecipeMonthlyUseCaseImpl(recipeMonthlyRepository = get()) }
    factory<UpdateRecipeNameAndDescriptionUseCase> { UpdateRecipeNameAndDescriptionUseCaseImpl(recipeRepository = get()) }
    factory<GetAllRecipeMonthUseCase> { GetAllRecipeMonthUseCaseImpl(recipePerMonthlyRepository = get()) }
    factory<GetByIdRecipeMonthlyUseCase> { GetByIdRecipeMonthlyUseCaseImpl(recipeMonthlyRepository = get()) }
    factory<DeleteRecipeUseCase> { DeleteRecipeUseCaseImpl(recipeRepository = get()) }
}
