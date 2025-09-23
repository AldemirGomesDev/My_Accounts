package br.com.aldemir.common.component

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.button_add_text
import org.jetbrains.compose.resources.stringResource

@Composable
fun FabAdd(
    onFabClicked: () -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked()
        },
        backgroundColor = MyAccountsTheme.colors.backgroundGreen
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(Res.string.button_add_text),
            tint = Color.White
        )
    }
}

@Composable
private fun FabAddPreview() {
    MyAccountsTheme {
        FabAdd(onFabClicked = {})
    }
}