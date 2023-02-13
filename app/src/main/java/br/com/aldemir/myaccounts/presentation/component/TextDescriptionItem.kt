package br.com.aldemir.myaccounts.presentation.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import br.com.aldemir.myaccounts.presentation.theme.Typography
import br.com.aldemir.myaccounts.presentation.theme.taskItemTextColor

@Composable
fun TextDescriptionItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colors.taskItemTextColor,
        style = Typography.caption,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}