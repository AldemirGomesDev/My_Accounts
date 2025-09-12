package br.com.aldemir.common.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.aldemir.common.theme.MyAccountsTheme

@Composable
fun TextTitleItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        color = MyAccountsTheme.colors.primary,
        style = MyAccountsTheme.typography.paragraph03Medium,
        maxLines = 1
    )
}