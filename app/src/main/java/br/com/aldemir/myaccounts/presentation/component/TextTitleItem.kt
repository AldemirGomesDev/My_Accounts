package br.com.aldemir.myaccounts.presentation.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import br.com.aldemir.myaccounts.presentation.theme.Typography
import br.com.aldemir.myaccounts.presentation.theme.taskItemTextColor

@Composable
fun TextTitleItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colors.taskItemTextColor,
        style = Typography.body1,
        fontWeight = FontWeight.Bold,
        maxLines = 1
    )
}