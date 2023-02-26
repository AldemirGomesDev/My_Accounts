package br.com.aldemir.myaccounts.domain.usecase.di


import br.com.aldemir.myaccounts.domain.usecase.recipe.add.AddRecipeMonthlyUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.add.AddRecipeMonthlyUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.recipe.add.AddRecipeUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.add.AddRecipeUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipe.GetAllRecipeUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipe.GetAllRecipeUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly.GetAllRecipeMonthlyUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly.GetAllRecipeMonthlyUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipepermonth.GetAllRecipePerMonthUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipepermonth.GetAllRecipePerMonthUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RecipeDomainModule {

    @Binds
    fun bindAddRecipeUseCase(useCase: AddRecipeUseCaseImpl) : AddRecipeUseCase

    @Binds
    fun bindAddRecipeMonthlyUseCase(useCase: AddRecipeMonthlyUseCaseImpl) : AddRecipeMonthlyUseCase

    @Binds
    fun bindGetAllRecipeUseCase(useCaseImpl: GetAllRecipeUseCaseImpl) : GetAllRecipeUseCase

    @Binds
    fun bindGetAllRecipePerMonthUseCase(useCaseImpl: GetAllRecipePerMonthUseCaseImpl) : GetAllRecipePerMonthUseCase

    @Binds
    fun bindGetAllRecipeMonthlyUseCase(useCaseImpl: GetAllRecipeMonthlyUseCaseImpl) : GetAllRecipeMonthlyUseCase
}