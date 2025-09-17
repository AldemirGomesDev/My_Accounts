package br.com.aldemir.common.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme

@Composable
fun TextSubTitleItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        color = MyAccountsTheme.colors.primary,
        style = MyAccountsTheme.typography.paragraph02Medium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun TextSubTitleItemPreview() {
    MyAccountsTheme {
        TextSubTitleItem(text = "Text Sub Title Item")
    }
}