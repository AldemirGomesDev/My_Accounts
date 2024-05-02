package br.com.aldemir.common.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.Typography
import br.com.aldemir.common.theme.taskItemTextColor

@Composable
fun TextBodyTwoItem(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MyAccountsTheme.colors.primary,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = Typography.body2,
        textAlign = TextAlign.End,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}