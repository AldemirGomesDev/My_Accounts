package br.com.aldemir.myaccounts.presentation.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import br.com.aldemir.myaccounts.presentation.theme.Typography
import br.com.aldemir.myaccounts.presentation.theme.taskItemTextColor

@Composable
fun TextMyButton(
    text: String
) {
    Text(
        text = text,
        color = MaterialTheme.colors.taskItemTextColor,
        style = Typography.button,
        maxLines = 1,
        textAlign = TextAlign.Center
    )
}