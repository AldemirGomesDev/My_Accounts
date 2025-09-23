package br.com.aldemir.common.component

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import br.com.aldemir.common.theme.White
import br.com.aldemir.common.theme.FontSize
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.button_no
import myaccounts.common.generated.resources.button_yes
import org.jetbrains.compose.resources.stringResource

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
                    Text(text = stringResource(Res.string.button_yes), color = White)
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
                        text = stringResource(Res.string.button_no),
                        color = MyAccountsTheme.colors.primary
                    )
                }
            },
            onDismissRequest = { closeDialog() }
        )
    }

}

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