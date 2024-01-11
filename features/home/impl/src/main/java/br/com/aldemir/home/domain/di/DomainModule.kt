package br.com.aldemir.home.domain.di

import br.com.aldemir.publ.domain.recipe.AddRecipeMonthlyUseCase
import br.com.aldemir.publ.domain.recipe.AddRecipeUseCase
import br.com.aldemir.publ.domain.recipe.DeleteRecipeUseCase
import br.com.aldemir.publ.domain.recipe.GetAllByIdRecipeUseCase
import br.com.aldemir.publ.domain.recipe.GetAllRecipeMonthlyUseCase
import br.com.aldemir.publ.domain.recipe.GetAllRecipePerMonthUseCase
import br.com.aldemir.publ.domain.recipe.GetAllRecipeUseCase
import br.com.aldemir.publ.domain.recipe.GetByIdRecipeMonthlyUseCase
import br.com.aldemir.publ.domain.recipe.UpdateRecipeMonthlyUseCase
import br.com.aldemir.publ.domain.recipe.UpdateRecipeNameAndDescriptionUseCase
import br.com.aldemir.publ.domain.darkmode.ReadDarkModeStateUseCase
import br.com.aldemir.home.domain.usecase.darkmode.ReadDarkModeStateUseCaseImpl
import br.com.aldemir.publ.domain.darkmode.SaveDarkModeStateUseCase
import br.com.aldemir.home.domain.usecase.darkmode.SaveDarkModeStateUseCaseImpl
import br.com.aldemir.publ.domain.expense.AddExpenseUseCase
import br.com.aldemir.home.domain.usecase.expense.add.AddExpenseUseCaseImpl
import br.com.aldemir.publ.domain.expense.AddMonthlyPaymentUseCase
import br.com.aldemir.home.domain.usecase.expense.add.AddMonthlyPaymentUseCaseImpl
import br.com.aldemir.publ.domain.expense.DeleteExpenseUseCase
import br.com.aldemir.home.domain.usecase.expense.delete.DeleteExpenseUseCaseImpl
import br.com.aldemir.home.domain.usecase.expense.getexpense.GetAllExpenseUseCase
import br.com.aldemir.home.domain.usecase.expense.getexpense.GetAllExpenseUseCaseImpl
import br.com.aldemir.publ.domain.expense.GetAllByIdExpenseUseCase
import br.com.aldemir.home.domain.usecase.expense.getexpensemonthly.GetAllByIdExpenseUseCaseImpl
import br.com.aldemir.publ.domain.expense.GetAllMonthlyPaymentUseCase
import br.com.aldemir.home.domain.usecase.expense.getexpensemonthly.GetAllMonthlyPaymentUseCaseImpl
import br.com.aldemir.publ.domain.expense.GetByIdMonthlyPaymentUseCase
import br.com.aldemir.home.domain.usecase.expense.getexpensemonthly.GetByIdMonthlyPaymentUseCaseImpl
import br.com.aldemir.publ.domain.expense.GetAllExpensePerMonthUseCase
import br.com.aldemir.home.domain.usecase.expense.getexpensepermonth.GetAllExpensePerMonthUseCaseImpl
import br.com.aldemir.publ.domain.expense.GetAllExpensesMonthUseCase
import br.com.aldemir.home.domain.usecase.expense.getexpensepermonth.GetAllExpensesMonthUseCaseImpl
import br.com.aldemir.publ.domain.expense.UpdateMonthlyPaymentUseCase
import br.com.aldemir.home.domain.usecase.expense.update.UpdateMonthlyPaymentUseCaseImpl
import br.com.aldemir.home.domain.usecase.recipe.add.AddRecipeMonthlyUseCaseImpl
import br.com.aldemir.home.domain.usecase.recipe.add.AddRecipeUseCaseImpl
import br.com.aldemir.home.domain.usecase.recipe.delete.DeleteRecipeUseCaseImpl
import br.com.aldemir.home.domain.usecase.recipe.getrecipe.GetAllRecipeUseCaseImpl
import br.com.aldemir.home.domain.usecase.recipe.getrecipemonthly.GetAllByIdRecipeUseCaseImpl
import br.com.aldemir.home.domain.usecase.recipe.getrecipemonthly.GetAllRecipeMonthlyUseCaseImpl
import br.com.aldemir.home.domain.usecase.recipe.getrecipemonthly.GetByIdRecipeMonthlyUseCaseImpl
import br.com.aldemir.home.domain.usecase.recipe.getrecipepermonth.GetAllRecipeMonthUseCase
import br.com.aldemir.home.domain.usecase.recipe.getrecipepermonth.GetAllRecipeMonthUseCaseImpl
import br.com.aldemir.home.domain.usecase.recipe.getrecipepermonth.GetAllRecipePerMonthUseCaseImpl
import br.com.aldemir.home.domain.usecase.recipe.update.UpdateRecipeMonthlyUseCaseImpl
import br.com.aldemir.home.domain.usecase.recipe.update.UpdateRecipeNameAndDescriptionUseCaseImpl
import org.koin.dsl.module

val domainModule = module {

    factory<GetAllExpenseUseCase> { GetAllExpenseUseCaseImpl(expenseRepository = get()) }
    factory<DeleteExpenseUseCase> { DeleteExpenseUseCaseImpl(expenseRepository = get()) }
    factory<AddExpenseUseCase> { AddExpenseUseCaseImpl(expenseRepository = get()) }
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
