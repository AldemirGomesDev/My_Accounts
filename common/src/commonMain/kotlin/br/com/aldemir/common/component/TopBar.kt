package br.com.aldemir.common.component

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
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import br.com.aldemir.common.theme.White
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.home_title
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun TopBar(
    titleResId: StringResource,
    imageIcon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(titleResId),
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

@Composable
private fun TopBarPreview() {
    MyAccountsTheme {
        TopBar(
            titleResId = Res.string.home_title,
            imageIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = {}
        )
    }
}