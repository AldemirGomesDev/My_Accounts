package br.com.aldemir.myaccounts.presentation.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.presentation.component.TextTitleLarge
import br.com.aldemir.myaccounts.presentation.theme.*

@Composable
fun DrawerHeader() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(100.dp)
            .fillMaxSize()
            .background(color = Purple200)
            .clip(
                RoundedCornerShape(topEnd = 40.dp)),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = MEDIUM_PADDING)
        ) {
            Image(
                modifier = Modifier
                    .size(LOGO_HEIGHT_MEDIUM),
                painter = painterResource(id = R.drawable.icon_despesa),
                contentDescription = stringResource(id = R.string.to_do_logo)
            )
            TextTitleLarge(
                text = "Bem Vindo!",
                modifier = Modifier.padding(start = LARGE_PADDING)
            )
        }
    }
}