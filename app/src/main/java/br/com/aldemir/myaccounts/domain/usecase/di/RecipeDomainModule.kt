package br.com.aldemir.myaccounts.domain.usecase.di


import br.com.aldemir.myaccounts.domain.usecase.recipe.AddRecipeMonthlyUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.AddRecipeMonthlyUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.recipe.AddRecipeUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.AddRecipeUseCaseImpl
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
}