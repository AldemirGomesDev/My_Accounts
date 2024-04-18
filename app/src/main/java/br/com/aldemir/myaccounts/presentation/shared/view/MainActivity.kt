package br.com.aldemir.myaccounts.presentation.shared.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import br.com.aldemir.myaccounts.presentation.drawer.DrawerNavigationScreen
import br.com.aldemir.expense.presentation.listexpense.ListExpenseViewModel
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    private val viewModel: ListExpenseViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.readDarkModeState()

        setContent {
            val uiState by viewModel.uiState.collectAsState()

            MyAccountsTheme(uiState.darkMode) {
                DrawerNavigationScreen(
                    switchState = uiState.darkMode,
                    onDarkMode = {
                        viewModel.setDarkMode()
                    }
                )
            }
        }
    }
}