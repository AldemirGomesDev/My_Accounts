package br.com.aldemir.common.component

import android.content.res.Configuration
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import br.com.aldemir.common.theme.Typography
import br.com.aldemir.common.theme.White

@Composable
fun TextTitleLarge(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MyAccountsTheme.colors.second,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MyAccountsTheme.typography.titleBold,
        textAlign = textAlign,
        maxLines = 1
    )
}


@PreviewLightDark
@Composable
private fun TextTitleLargePreview() {
    MyAccountsTheme {
        TextTitleLarge(text = "TextTitleLarge")
    }
}