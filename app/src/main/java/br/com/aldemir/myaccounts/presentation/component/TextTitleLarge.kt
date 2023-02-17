package br.com.aldemir.myaccounts.presentation.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import br.com.aldemir.myaccounts.presentation.theme.Typography
import br.com.aldemir.myaccounts.presentation.theme.White

@Composable
fun TextTitleLarge(
    text: String,
    color: Color = White,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = Typography.h5,
        fontWeight = FontWeight.Bold,
        maxLines = 1
    )
}