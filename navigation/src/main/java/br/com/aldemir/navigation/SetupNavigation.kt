package br.com.aldemir.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.aldemir.navigation.destinations.authentication.authenticationComposable
import br.com.aldemir.navigation.destinations.expense.expenseGraph
import br.com.aldemir.navigation.destinations.recipe.addRecipeComposable
import br.com.aldemir.navigation.destinations.recipe.changeRecipeComposable
import br.com.aldemir.navigation.destinations.recipe.detailRecipeComposable
import br.com.aldemir.navigation.destinations.recipe.listRecipeComposable
import br.com.aldemir.navigation.destinations.shared.historicComposable
import br.com.aldemir.navigation.destinations.shared.homeComposable
import br.com.aldemir.navigation.destinations.shared.splashComposable

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navHostController: NavHostController,
    startDestination: String,
    isDarkTheme: Boolean,
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        splashComposable(isDarkTheme, navHostController)

        homeComposable(navHostController)

        authenticationComposable(isDarkTheme, navHostController)

        expenseGraph(navHostController = navHostController)

        historicComposable(navHostController)

        addRecipeComposable(navHostController)

        listRecipeComposable(navHostController)

        detailRecipeComposable(navHostController)

        changeRecipeComposable(navHostController)
    }
}