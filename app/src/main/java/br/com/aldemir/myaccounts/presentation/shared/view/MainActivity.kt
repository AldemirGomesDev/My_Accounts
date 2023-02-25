package br.com.aldemir.myaccounts.presentation.shared.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import br.com.aldemir.myaccounts.presentation.drawer.DrawerNavigationScreen
import br.com.aldemir.myaccounts.presentation.home.HomeViewModel
import br.com.aldemir.myaccounts.presentation.theme.MyAccountsTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.readDarkModeState()

        setContent {
            MyAccountsTheme(viewModel.uiState.value.darkMode) {
                DrawerNavigationScreen(viewModel)
            }
        }
    }
}