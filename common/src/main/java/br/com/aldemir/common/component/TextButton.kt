package br.com.aldemir.common.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import br.com.aldemir.common.theme.Typography
import br.com.aldemir.common.theme.taskItemTextColor

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