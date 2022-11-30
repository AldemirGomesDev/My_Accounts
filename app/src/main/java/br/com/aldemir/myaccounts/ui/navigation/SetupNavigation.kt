package br.com.aldemir.myaccounts.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import br.com.aldemir.myaccounts.ui.navigation.destinations.homeComposable
import br.com.aldemir.myaccounts.ui.navigation.destinations.splashComposable
import br.com.aldemir.myaccounts.util.Const.SPLASH_SCREEN
import com.google.accompanist.navigation.animation.AnimatedNavHost

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController
)
{
    val screen = remember(navController) {
        Screens(navController = navController)
    }


    AnimatedNavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN
    ) {
        splashComposable(navigateToListScreen = screen.splash)
        homeComposable(navigateToListScreen = screen.home)
    }
}