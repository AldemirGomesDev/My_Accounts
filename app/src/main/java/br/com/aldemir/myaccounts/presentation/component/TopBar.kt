package br.com.aldemir.myaccounts.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.aldemir.myaccounts.presentation.theme.White
import br.com.aldemir.myaccounts.presentation.theme.topAppBarBackGroundColor

@Composable
fun TopBar(
    titleResId: Int,
    imageIcon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = titleResId), color = White)
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
        backgroundColor = MaterialTheme.colors.topAppBarBackGroundColor,
        modifier = modifier
    )
}