package br.com.aldemir.myaccounts.presentation.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import br.com.aldemir.myaccounts.presentation.theme.Typography
import br.com.aldemir.myaccounts.presentation.theme.White

@Composable
fun TextTitleLarge(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = White,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = Typography.h5,
        fontWeight = FontWeight.Bold,
        textAlign = textAlign,
        maxLines = 1
    )
}