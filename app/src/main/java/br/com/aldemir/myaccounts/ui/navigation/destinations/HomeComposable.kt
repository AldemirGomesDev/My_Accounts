package br.com.aldemir.myaccounts.ui.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import br.com.aldemir.myaccounts.util.Const.HOME_SCREEN
import br.com.aldemir.myaccounts.util.Const.LIST_ARGUMENT_KEY
import com.google.accompanist.navigation.animation.composable


@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.homeComposable(
    navigateToListScreen: () -> Unit,
) {
    composable(
        route = HOME_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->


//        HomeFragment(navigateToListScreen = navigateToListScreen)
    }
}
