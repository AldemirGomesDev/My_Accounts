package br.com.aldemir.myaccounts.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import br.com.aldemir.myaccounts.presentation.expense.listexpense.ListExpenseViewModel
import br.com.aldemir.myaccounts.presentation.navigation.destinations.*
import br.com.aldemir.myaccounts.presentation.navigation.destinations.recipe.addRecipeComposable
import br.com.aldemir.myaccounts.presentation.navigation.destinations.recipe.detailRecipeComposable
import br.com.aldemir.myaccounts.presentation.navigation.destinations.recipe.listRecipeComposable
import com.google.accompanist.navigation.animation.AnimatedNavHost

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navHostController: NavHostController,
    startDestination: String,
    viewModel: ListExpenseViewModel
)
{
    AnimatedNavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        splashComposable(navHostController)

        homeComposable(navHostController)

        listExpenseComposable(
            navHostController = navHostController,
            viewModel = viewModel
        )

        addAccountComposable(navHostController)

        detailExpenseComposable(navHostController)

        changeExpenseComposable(navHostController)

        historicComposable(navHostController)

        addRecipeComposable(navHostController)

        listRecipeComposable(navHostController)

        detailRecipeComposable(navHostController)
    }
}