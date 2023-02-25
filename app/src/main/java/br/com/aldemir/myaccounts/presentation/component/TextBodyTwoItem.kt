package br.com.aldemir.myaccounts.presentation.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import br.com.aldemir.myaccounts.presentation.theme.Typography
import br.com.aldemir.myaccounts.presentation.theme.taskItemTextColor

@Composable
fun TextBodyTwoItem(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.taskItemTextColor,
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