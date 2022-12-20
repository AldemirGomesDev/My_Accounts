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
    navHostController: NavHostController
)
{
    AnimatedNavHost(
        navController = navHostController,
        startDestination = Route.Splash.route
    ) {
        splashComposable(navHostController)

        homeComposable(navHostController)

        addAccountComposable(navHostController)

        detailExpenseComposable(navHostController)

        changeExpenseComposable(navHostController)
    }
}