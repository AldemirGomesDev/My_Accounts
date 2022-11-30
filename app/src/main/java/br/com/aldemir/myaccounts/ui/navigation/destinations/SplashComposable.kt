package br.com.aldemir.myaccounts.ui.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import br.com.aldemir.myaccounts.ui.SplashScreen
import br.com.aldemir.myaccounts.util.Const
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit,
) {
    composable(
        route = Const.SPLASH_SCREEN,

    ) {
        SplashScreen(navigateToListScreen = navigateToListScreen)
    }
}