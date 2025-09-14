package br.com.aldemir.myaccounts.presentation.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.aldemir.common.theme.AppDarkMode
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import br.com.aldemir.myaccounts.presentation.action.MainAction
import br.com.aldemir.navigation.drawer.DrawerNavigationScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun MyAccountsApp(
    viewModel: MainViewModel = koinViewModel()
) {
    viewModel.onAction(MainAction.FetchData)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val isDarkMode = when(uiState.appDarkMode) {
        AppDarkMode.Default -> isSystemInDarkTheme()
        AppDarkMode.Dark -> true
        AppDarkMode.Light -> false
    }

    MyAccountsTheme {
        DrawerNavigationScreen(
            isDarkTheme = isDarkMode,
            listItems = uiState.listItems,
            onItemClicked = {
                viewModel.onAction(MainAction.UpdateDarkModeState(it))
            },
            darkModeStateSelected = uiState.appDarkMode
        )
    }
}