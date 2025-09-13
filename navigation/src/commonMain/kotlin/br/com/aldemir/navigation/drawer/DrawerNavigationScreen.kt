package br.com.aldemir.navigation.drawer

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.aldemir.common.component.TopBar
import br.com.aldemir.common.theme.AppDarkMode
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.navigation.R
import br.com.aldemir.navigation.Routes
import br.com.aldemir.navigation.SetupNavigation
import br.com.aldemir.navigation.bottomappbar.BottomBar
import br.com.aldemir.navigation.state.TopBarState
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun DrawerNavigationScreen(
    isDarkTheme: Boolean,
    listItems: List<AppDarkMode>,
    onItemClicked: (state: AppDarkMode) -> Unit,
    darkModeStateSelected: AppDarkMode,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val scaffoldState = rememberScaffoldState(drawerState)

    val navController = rememberNavController()

    val currentRoute = currentRoute(navController)

    val scope = rememberCoroutineScope()

    val topBarState = getTopBarState(navController)

    val openDrawer = {
        scope.launch {
            scaffoldState.drawerState.open()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (topBarState.isVisible) {
                TopBar(
                    titleResId = topBarState.titleResId,
                    imageIcon = topBarState.imageIcon,
                    onClick = {
                        if (topBarState.isHome) {
                            openDrawer.invoke()
                        } else {
                            topBarState.onClick()
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (currentRoute != Routes.Splash.toString()
                && currentRoute != null
                && currentRoute != Routes.Login.toString()
                && currentRoute != Routes.Register.toString()
            ) {
                BottomBar(navController = navController)
            }
        },
        drawerGesturesEnabled = true,
        drawerContent = {
            DrawerHeader(
                listItems = listItems,
                onItemClicked = onItemClicked,
                darkModeStateSelected = darkModeStateSelected,
            )
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
            Box(
                modifier = Modifier
                    .padding(it)
                    .background(MyAccountsTheme.colors.background)
            ) {
                SetupNavigation(
                    isDarkTheme = isDarkTheme,
                    navHostController = navController,
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

@Composable
fun getTopBarState(navController: NavHostController): TopBarState {
    val route = currentRoute(navController = navController)?.substringAfterLast(".")
    return when (route) {
        Routes.Splash.toString() -> {
            TopBarState(
                onClick = {}
            )
        }

        Routes.Login.toString() -> {
            TopBarState(
                onClick = {}
            )
        }

        Routes.Home.toString() -> {
            TopBarState(
                titleResId = R.string.app_name,
                imageIcon = Icons.Default.Menu,
                isHome = true,
                isVisible = true,
                onClick = { navController.navigateUp() }
            )
        }

        Routes.Historic.toString() -> {
            TopBarState(
                isVisible = true,
                titleResId = R.string.historic_screen_title,
                imageIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = { navController.navigateUp() }

            )
        }

        Routes.AddRecipe.toString() -> {
            TopBarState(
                isVisible = true,
                titleResId = R.string.recipe_add_screen_title,
                imageIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = { navController.navigateUp() }
            )
        }

        Routes.ListRecipe.toString() -> {
            TopBarState(
                isVisible = true,
                titleResId = R.string.recipe_list_screen_title,
                imageIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = { navController.navigateUp() }
            )
        }

        Routes.DetailRecipe.toString() -> {
            TopBarState(
                isVisible = true,
                titleResId = R.string.recipe_detail_screen_title,
                imageIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = { navController.navigateUp() }
            )
        }

        Routes.ChangeRecipe.toString() -> {
            TopBarState(
                isVisible = true,
                titleResId = R.string.recipe_change_screen_title,
                imageIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = { navController.navigateUp() }
            )
        }

        Routes.ExpenseGraphRoute.toString() -> {
            TopBarState(
                isVisible = true,
                titleResId = R.string.expense_list_screen_title,
                imageIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = { navController.navigateUp() }
            )
        }

        Routes.ExpenseGraphRoute.ExpenseAdd.toString() -> {
            TopBarState(
                isVisible = true,
                titleResId = R.string.expense_add_screen_title,
                imageIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = { navController.navigateUp() }
            )
        }

        Routes.ExpenseGraphRoute.ExpenseList.toString() -> {
            TopBarState(
                isVisible = true,
                titleResId = R.string.expense_list_screen_title,
                imageIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = { navController.navigateUp() }
            )
        }

        Routes.ExpenseGraphRoute.ExpenseDetail.toString() -> {
            TopBarState(
                isVisible = true,
                titleResId = R.string.expense_detail_screen_title,
                imageIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = { navController.navigateUp() }
            )
        }

        Routes.ExpenseGraphRoute.ExpenseChange.toString() -> {
            TopBarState(
                isVisible = true,
                titleResId = R.string.expense_change_screen_title,
                imageIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = { navController.navigateUp() }
            )
        }

        else -> {
            TopBarState(onClick = { navController.navigateUp() })
        }
    }
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class
)
@Preview(showSystemUi = true)
@Composable
fun DrawerNavigationScreenPreview() {
    DrawerNavigationScreen(
        isDarkTheme = true,
        darkModeStateSelected = AppDarkMode.Default,
        onItemClicked = {},
        listItems = listOf()
    )
}