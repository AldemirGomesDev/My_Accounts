package br.com.aldemir.myaccounts.domain.usecase.di


import br.com.aldemir.myaccounts.domain.usecase.recipe.add.AddRecipeMonthlyUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.add.AddRecipeMonthlyUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.recipe.add.AddRecipeUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.add.AddRecipeUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipe.GetAllRecipeUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipe.GetAllRecipeUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly.GetAllByIdRecipeUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly.GetAllByIdRecipeUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly.GetAllRecipeMonthlyUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly.GetAllRecipeMonthlyUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipepermonth.GetAllRecipePerMonthUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipepermonth.GetAllRecipePerMonthUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.recipe.update.UpdateRecipeMonthlyUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.update.UpdateRecipeMonthlyUseCaseImpl
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
    fun bindGetAllRecipeUseCase(useCase: GetAllRecipeUseCaseImpl) : GetAllRecipeUseCase

    @Binds
    fun bindGetAllRecipePerMonthUseCase(useCase: GetAllRecipePerMonthUseCaseImpl) : GetAllRecipePerMonthUseCase

    @Binds
    fun bindGetAllRecipeMonthlyUseCase(useCase: GetAllRecipeMonthlyUseCaseImpl) : GetAllRecipeMonthlyUseCase

    @Binds
    fun bindGetAllByIdRecipeMonthlyUseCase(useCase: GetAllByIdRecipeUseCaseImpl) : GetAllByIdRecipeUseCase

    @Binds
    fun bindUpdateRecipeMonthlyUseCase(useCase: UpdateRecipeMonthlyUseCaseImpl) : UpdateRecipeMonthlyUseCase
}