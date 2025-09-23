package br.com.aldemir.myaccounts.presentation.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.fragment.app.FragmentActivity
import br.com.aldemir.myaccounts.MyAccountsApp

@ExperimentalComposeUiApi
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAccountsApp()
        }
    }
}