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
import androidx.navigation.compose.rememberNavController
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.presentation.bottomappbar.BottomBar
import br.com.aldemir.common.component.TopBar
import br.com.aldemir.expense.presentation.listexpense.ListExpenseViewModel
import br.com.aldemir.myaccounts.presentation.navigation.Route
import br.com.aldemir.myaccounts.presentation.navigation.SetupNavigation
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun DrawerNavigationScreen(
    viewModel: ListExpenseViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val scaffoldState = rememberScaffoldState(drawerState)

    val navController = rememberNavController()

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
                    TopBar(titleResId = R.string.expense_add_screen_title,
                        imageIcon = Icons.Filled.ArrowBack,
                        onClick = { navController.navigateUp() }
                    )
                }
                Route.ExpenseList.route -> {
                    TopBar(titleResId = R.string.expense_list,
                        imageIcon = Icons.Default.ArrowBack,
                        onClick = {
                            navController.navigate(
                                Route.Home.route
                            )
                        }
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
                    TopBar(titleResId = R.string.historic_screen_title,
                        imageIcon = Icons.Filled.ArrowBack,
                        onClick = { navController.navigateUp() }
                    )
                }
                Route.AddRecipe.route -> {
                    TopBar(
                        titleResId = R.string.recipe_add_screen_title,
                        imageIcon = Icons.Filled.ArrowBack,
                        onClick = { navController.navigateUp() }
                    )
                }
                Route.ListRecipe.route -> {
                    TopBar(
                        titleResId = R.string.recipe_list_screen_title,
                        imageIcon = Icons.Filled.ArrowBack,
                        onClick = {
                            navController.navigate(
                                Route.Home.route
                            )
                        }
                    )
                }
                Route.DetailRecipe.route -> {
                    TopBar(
                        titleResId = R.string.recipe_detail_screen_title,
                        imageIcon = Icons.Filled.ArrowBack,
                        onClick = { navController.navigateUp() }
                    )
                }
                Route.ChangeRecipe.route -> {
                    TopBar(
                        titleResId = R.string.recipe_change_screen_title,
                        imageIcon = Icons.Filled.ArrowBack,
                        onClick = { navController.navigateUp() }
                    )
                }
            }
        },
        bottomBar = {
            if (currentRoute(navController) != Route.Splash.route){
                BottomBar(navController = navController)
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