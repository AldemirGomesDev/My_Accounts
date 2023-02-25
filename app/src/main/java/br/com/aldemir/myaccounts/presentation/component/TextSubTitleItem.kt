package br.com.aldemir.myaccounts.presentation.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import br.com.aldemir.myaccounts.presentation.theme.Typography
import br.com.aldemir.myaccounts.presentation.theme.taskItemTextColor

@Composable
fun TextSubTitleItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        color = MaterialTheme.colors.taskItemTextColor,
        style = Typography.subtitle2,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}