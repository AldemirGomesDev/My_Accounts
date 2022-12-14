package br.com.aldemir.myaccounts.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import br.com.aldemir.myaccounts.ui.navigation.SetupNavigation
import br.com.aldemir.myaccounts.ui.theme.MyAccountsTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
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
                val navHostController = rememberAnimatedNavController()
                SetupNavigation(navHostController = navHostController)
            }
        }

    }
}