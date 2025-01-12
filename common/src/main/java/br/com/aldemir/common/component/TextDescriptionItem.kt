package br.com.aldemir.common.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import br.com.aldemir.common.theme.MyAccountsTheme

@Composable
fun TextDescriptionItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        color = MyAccountsTheme.colors.primary,
        style = MyAccountsTheme.typography.paragraph01,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}