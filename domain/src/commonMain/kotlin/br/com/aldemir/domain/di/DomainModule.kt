package br.com.aldemir.domain.di

import br.com.aldemir.domain.usecase.authentication.GetLoggerUserUseCase
import br.com.aldemir.domain.usecase.authentication.InsertUserUseCase
import br.com.aldemir.domain.usecase.authentication.LoginUseCase
import br.com.aldemir.domain.usecase.authentication.LogoutUseCase
import br.com.aldemir.domain.usecase.counttime.ClearStartTimeUseCase
import br.com.aldemir.domain.usecase.counttime.GetFaceMatchTimerStatusUseCase
import br.com.aldemir.domain.usecase.counttime.SaveStartTimeUseCase
import br.com.aldemir.domain.usecase.counttime.SetCountTimeFinishedUseCase
import br.com.aldemir.domain.usecase.darkmode.ReadDarkModeStateUseCase
import br.com.aldemir.domain.usecase.darkmode.SaveDarkModeStateUseCase
import br.com.aldemir.domain.usecase.expense.AddExpenseUseCase
import br.com.aldemir.domain.usecase.expense.AddMonthlyPaymentUseCase
import br.com.aldemir.domain.usecase.expense.DeleteExpenseUseCase
import br.com.aldemir.domain.usecase.expense.GetAllExpenseUseCase
import br.com.aldemir.domain.usecase.expense.GetAllByIdExpenseUseCase
import br.com.aldemir.domain.usecase.expense.GetAllMonthlyPaymentUseCase
import br.com.aldemir.domain.usecase.expense.GetByIdMonthlyPaymentUseCase
import br.com.aldemir.domain.usecase.expense.GetAllExpensePerMonthUseCase
import br.com.aldemir.domain.usecase.expense.GetAllExpensesMonthUseCase
import br.com.aldemir.domain.usecase.expense.UpdateExpenseSituationUseCase
import br.com.aldemir.domain.usecase.expense.UpdateExpenseValueUseCase
import br.com.aldemir.domain.usecase.post.GetAllPostsUseCase
import br.com.aldemir.domain.usecase.product.GetAllProductsUseCase
import br.com.aldemir.domain.usecase.recipe.AddRecipeMonthlyUseCase
import br.com.aldemir.domain.usecase.recipe.AddRecipeUseCase
import br.com.aldemir.domain.usecase.recipe.DeleteRecipeUseCase
import br.com.aldemir.domain.usecase.recipe.GetAllRecipeUseCase
import br.com.aldemir.domain.usecase.recipe.GetAllByIdRecipeUseCase
import br.com.aldemir.domain.usecase.recipe.GetAllRecipeMonthlyUseCase
import br.com.aldemir.domain.usecase.recipe.GetByIdRecipeMonthlyUseCase
import br.com.aldemir.domain.usecase.recipe.GetAllRecipeMonthUseCase
import br.com.aldemir.domain.usecase.recipe.GetAllRecipePerMonthUseCase
import br.com.aldemir.domain.usecase.recipe.UpdateRecipeSituationUseCase
import br.com.aldemir.domain.usecase.recipe.UpdateRecipeMonthlyUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::AddExpenseUseCase)
    factoryOf(::AddMonthlyPaymentUseCase)
    factoryOf(::GetAllExpenseUseCase)
    factoryOf(::DeleteExpenseUseCase)
    factoryOf(::GetAllByIdExpenseUseCase)
    factoryOf(::GetAllExpensePerMonthUseCase)
    factoryOf(::GetAllMonthlyPaymentUseCase)
    factoryOf(::GetByIdMonthlyPaymentUseCase)
    factoryOf(::UpdateExpenseSituationUseCase)
    factoryOf(::UpdateExpenseValueUseCase)
    factoryOf(::GetAllExpensesMonthUseCase)
    // Dark mode
    factoryOf(::SaveDarkModeStateUseCase)
    factoryOf(::ReadDarkModeStateUseCase)
    // Recipe
    factoryOf(::AddRecipeMonthlyUseCase)
    factoryOf(::AddRecipeUseCase)
    factoryOf(::DeleteRecipeUseCase)
    factoryOf(::GetAllByIdRecipeUseCase)
    factoryOf(::GetAllRecipeMonthlyUseCase)
    factoryOf(::GetAllRecipePerMonthUseCase)
    factoryOf(::GetAllRecipeUseCase)
    factoryOf(::GetAllRecipeMonthUseCase)
    factoryOf(::GetByIdRecipeMonthlyUseCase)
    factoryOf(::UpdateRecipeSituationUseCase)
    factoryOf(::UpdateRecipeMonthlyUseCase)
    factoryOf(::InsertUserUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::LogoutUseCase)
    factoryOf(::GetAllPostsUseCase)
    factoryOf(::GetAllProductsUseCase)
    factoryOf(::GetLoggerUserUseCase)
    // count time
    factoryOf(::SaveStartTimeUseCase)
    factoryOf(::GetFaceMatchTimerStatusUseCase)
    factoryOf(::ClearStartTimeUseCase)
    factoryOf(::SetCountTimeFinishedUseCase)
}
