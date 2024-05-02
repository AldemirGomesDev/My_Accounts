package br.com.aldemir.common.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.Typography
import br.com.aldemir.common.theme.taskItemTextColor

@Composable
fun TextDescriptionItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        color = MyAccountsTheme.colors.primary,
        style = Typography.caption,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}