package br.com.aldemir.myaccounts.presentation.drawer


import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.presentation.component.TopBar
import br.com.aldemir.myaccounts.presentation.home.HomeViewModel
import br.com.aldemir.myaccounts.presentation.navigation.Route
import br.com.aldemir.myaccounts.presentation.navigation.SetupNavigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun DrawerNavigationScreen(
    viewModel: HomeViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val scaffoldState = rememberScaffoldState(drawerState)

    val navController = rememberAnimatedNavController()

    val scope = rememberCoroutineScope()

    val openDrawer = {
        scope.launch {
            scaffoldState.drawerState.open()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            when(currentRoute(navController)) {
                Route.Home.route -> {
                    TopBar(titleResId = R.string.app_name,
                        imageIcon = Icons.Default.Menu,
                        onClick = { openDrawer() }
                    )
                }
                Route.ExpenseAdd.route -> {
                    TopBar(titleResId = R.string.add_account_screen_title,
                        imageIcon = Icons.Filled.ArrowBack,
                        onClick = { navController.navigateUp() }
                    )
                }
                Route.ExpenseDetail.route -> {
                    TopBar(titleResId = R.string.expense_detail_screen_title,
                        imageIcon = Icons.Filled.ArrowBack,
                        onClick = { navController.navigateUp() }
                    )
                }
                Route.ExpenseChange.route -> {
                    TopBar(titleResId = R.string.expense_change_screen_title,
                        imageIcon = Icons.Filled.ArrowBack,
                        onClick = { navController.navigateUp() }
                    )
                }
                Route.Historic.route -> {
                    TopBar(titleResId = R.string.historic,
                        imageIcon = Icons.Filled.ArrowBack,
                        onClick = { navController.navigateUp() }
                    )
                }
                Route.AddRecipe.route -> {
                    TopBar(
                        titleResId = R.string.add_revenue_screen_title,
                        imageIcon = Icons.Filled.ArrowBack,
                        onClick = { navController.navigateUp() }
                    )
                }
                Route.ListRecipe.route -> {
                    TopBar(
                        titleResId = R.string.list_recipe_screen_title,
                        imageIcon = Icons.Filled.ArrowBack,
                        onClick = { navController.navigateUp() }
                    )
                }
            }
        },
        drawerGesturesEnabled = true,
        drawerContent = {
            DrawerHeader()
            DrawerBody(
                menuItems = screens,
                scaffoldState,
                scope
            ) {
                navController.navigate(it.route) {
                    launchSingleTop = true
                }
            }
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                SetupNavigation(
                    navHostController = navController,
                    startDestination = Route.Splash.route,
                    viewModel = viewModel
                )
            }
        }
    )
}


@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}