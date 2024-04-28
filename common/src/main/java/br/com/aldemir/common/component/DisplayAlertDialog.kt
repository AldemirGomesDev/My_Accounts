package br.com.aldemir.common.component

import android.content.res.Configuration
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import br.com.aldemir.common.theme.White
import br.com.aldemir.common.R
import br.com.aldemir.common.theme.FontSize
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit,
) {
    if (openDialog) {
        AlertDialog(
            backgroundColor = MyAccountsTheme.colors.background,
            title = {
                Text(
                    text = title,
                    fontSize = FontSize.scale20,
                    fontWeight = FontWeight.Bold,
                    color = MyAccountsTheme.colors.primary,
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = MyAccountsTheme.colors.primary,
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onYesClicked()
                        closeDialog()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MyAccountsTheme.colors.primary
                    ),
                ) {
                    Text(text = stringResource(R.string.button_yes), color = White)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        closeDialog()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MyAccountsTheme.colors.background
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.button_no),
                        color = MyAccountsTheme.colors.primary
                    )
                }
            },
            onDismissRequest = { closeDialog() }
        )
    }

}

@Preview(
    name = "Light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun DisplayAlertDialogPreview() {
    MyAccountsTheme {
        Surface {
            DisplayAlertDialog(
                "Aviso",
                "Deseja realmente excluir?",
                true,
                {},
                {}
            )
        }
    }
}