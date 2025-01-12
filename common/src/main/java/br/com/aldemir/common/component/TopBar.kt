package br.com.aldemir.common.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.R
import br.com.aldemir.common.theme.MyAccountsFont
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import br.com.aldemir.common.theme.White

@Composable
fun TopBar(
    titleResId: Int,
    imageIcon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = titleResId),
                color = White,
                style = MyAccountsTheme.typography.subTitleMedium
            )
        },
        navigationIcon = {
            Icon(
                imageVector = imageIcon,
                tint = White,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        onClick()
                    },
                contentDescription = null
            )
        },
        backgroundColor = MyAccountsTheme.colors.backgroundGreen,
        modifier = modifier
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun TopBarPreview() {
    MyAccountsTheme {
        TopBar(
            titleResId = R.string.app_name,
            imageIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = {}
        )
    }
}