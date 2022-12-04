package br.com.aldemir.myaccounts.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.aldemir.myaccounts.ui.SplashScreen
import br.com.aldemir.myaccounts.ui.expense.AddAccountScreen
import br.com.aldemir.myaccounts.ui.expensechange.ChangeExpenseScreen
import br.com.aldemir.myaccounts.ui.expensedetail.ExpenseDetailScreen
import br.com.aldemir.myaccounts.ui.navigation.Route
import br.com.aldemir.myaccounts.ui.theme.MyAccountsTheme
import br.com.aldemir.myaccounts.util.Const.EXPENSE_ID
import br.com.aldemir.myaccounts.util.Const.EXPENSE_NAME
import br.com.aldemir.myaccounts.util.Const.TAG
import br.com.aldemir.myaccounts.util.emptyString
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyAccountsTheme {
                val navHostController = rememberNavController()
                NavHost(
                    navController = navHostController,
                    startDestination = Route.Splash.route
                ) {
                    composable(route = Route.Splash.route) {
                        SplashScreen(navigateToListScreen = {
                            navHostController.navigate(Route.Home.route) {
                                popUpTo(Route.Splash.route) {
                                    inclusive = true
                                }
                            }
                        }
                        )
                    }
                    composable(route = Route.Home.route) {
                        HomeScreen(
                            navigateToTaskScreen = { expenseId, expenseName ->
                                navHostController.navigate(
                                    Route.ExpenseDetail.createRoute(expenseId, expenseName)
                                )
                            },
                            navigateToAddScreen = {
                                navHostController.navigate(
                                    Route.ExpenseAdd.route
                                )
                            }
                        )
                    }
                    composable(route = Route.ExpenseAdd.route) {
                        AddAccountScreen(
                            navigateToHomeScreen = {
                                navHostController.navigate(
                                    Route.Home.route
                                )
                            }
                        )
                    }
                    composable(route = Route.ExpenseDetail.route,
                        arguments = listOf(
                            navArgument(EXPENSE_ID) {
                                type = NavType.IntType
                            },
                            navArgument(EXPENSE_NAME) {
                                type = NavType.StringType
                            }
                        )
                    ) { backStackEntry ->
                        val expenseId = backStackEntry.arguments?.getInt(EXPENSE_ID)
                        val expenseName = backStackEntry.arguments?.getString(EXPENSE_NAME)
                        ExpenseDetailScreen(
                            expenseId = expenseId ?: 0,
                            expenseName = expenseName ?: "",
                            navigateToChangeScreen = { idMonthlyPayment ->
                                navHostController.navigate(
                                    Route.ExpenseChange.createRoute(
                                        idMonthlyPayment,
                                        expenseName ?: emptyString()
                                    )
                                )
                            }
                        )
                    }
                    composable(route = Route.ExpenseChange.route,
                        arguments = listOf(
                            navArgument(EXPENSE_ID) {
                                type = NavType.IntType
                            },
                            navArgument(EXPENSE_NAME) {
                                type = NavType.StringType
                            }
                        )
                    ) { backStackEntry ->
                        val idMonthlyPayment = backStackEntry.arguments?.getInt(EXPENSE_ID)
                        val expenseName = backStackEntry.arguments?.getString(EXPENSE_NAME)
                        ChangeExpenseScreen(
                            idMonthlyPayment = idMonthlyPayment ?: 0,
                            expenseName = expenseName ?: emptyString(),
                            navigateToDetailScreen = {
                                navHostController.navigateUp()
                            }
                        )
                    }
                }
            }
        }

    }
}