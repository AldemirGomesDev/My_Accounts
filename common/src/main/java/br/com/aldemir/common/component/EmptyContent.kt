package br.com.aldemir.common.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.R
import br.com.aldemir.common.theme.MediumGray
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme

@Composable
fun EmptyContent(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.expense_text)
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MyAccountsTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(120.dp),
            painter = painterResource(id = R.drawable.ic_sad_face),
            contentDescription = stringResource(R.string.sad_face_icon),
            tint = MediumGray
        )
        Text(
            text = context.getString(R.string.empty_content, text),
            color = MediumGray,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize
        )
    }
}

@Composable
@Preview(
    name = "Dark mode",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
)
@Preview(
    name = "Light mode",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
private fun EmptyContentPreview() {
    MyAccountsTheme {
        Surface {
            EmptyContent()
        }
    }
}