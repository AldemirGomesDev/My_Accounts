package br.com.aldemir.myaccounts.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.presentation.theme.MediumGray
import br.com.aldemir.myaccounts.util.emptyString

@Composable
fun EmptyContent(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background),
    text: String = stringResource(id = R.string.expense_text)
) {
    val context = LocalContext.current

    Column(
        modifier = modifier,
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
@Preview
private fun EmptyContentPreview() {
    EmptyContent()
}