package br.com.aldemir.myaccounts.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import br.com.aldemir.myaccounts.presentation.navigation.destinations.*
import com.google.accompanist.navigation.animation.AnimatedNavHost

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navHostController: NavHostController,
    startDestination: String
)
{
    AnimatedNavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        splashComposable(navHostController)

        homeComposable(navHostController)

        addAccountComposable(navHostController)

        detailExpenseComposable(navHostController)

        changeExpenseComposable(navHostController)

        historicComposable(navHostController)
    }
}