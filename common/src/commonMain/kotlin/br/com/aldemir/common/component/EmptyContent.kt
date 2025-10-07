package br.com.aldemir.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.theme.MediumGray
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.empty_content
import myaccounts.common.generated.resources.expense_text
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmptyContent(
    modifier: Modifier = Modifier,
    text: String = stringResource(Res .string.expense_text)
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MyAccountsTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .padding(top = MyAccountsTheme.dimensions.padding64)
                .size(120.dp),
            imageVector = Icons.Filled.Face,
            contentDescription = null,
            tint = MediumGray
        )
        Text(
            text = stringResource(Res.string.empty_content, text),
            color = MediumGray,
            style = MyAccountsTheme.typography.h4
        )
    }
}

@Composable
private fun EmptyContentPreview() {
    MyAccountsTheme {
        Surface {
            EmptyContent()
        }
    }
}