package br.com.aldemir.common.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import br.com.aldemir.common.theme.White
import br.com.aldemir.common.theme.topAppBarBackGroundColor

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

@Preview(showBackground = true)
@Composable
private fun TopBarPreview() {
    TopBar(
        titleResId = R.string.app_name,
        imageIcon = Icons.AutoMirrored.Filled.ArrowBack,
        onClick = {}
    )
}