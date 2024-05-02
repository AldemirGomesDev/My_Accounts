package br.com.aldemir.common.component

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.aldemir.common.R
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme

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
            contentDescription = stringResource(
                id = R.string.button_add_text
            ),
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FabAddPreview() {
    MyAccountsTheme {
        FabAdd(onFabClicked = {})
    }
}