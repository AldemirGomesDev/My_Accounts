package br.com.aldemir.myaccounts.presentation.navigation

import androidx.navigation.NavHostController
import br.com.aldemir.myaccounts.util.Const.SPLASH_SCREEN

class Screens(navController: NavHostController) {
    val splash: () -> Unit = {
        navController.navigate(route = "list/no_action") {
            popUpTo(SPLASH_SCREEN) { inclusive = true}
        }
    }

    val home: () -> Unit = {
        navController.navigate(route = "list/salve")
    }
}