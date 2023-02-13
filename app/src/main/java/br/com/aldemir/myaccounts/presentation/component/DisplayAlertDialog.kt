package br.com.aldemir.myaccounts.presentation.component

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.presentation.theme.Purple200
import br.com.aldemir.myaccounts.presentation.theme.White

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
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onYesClicked()
                        closeDialog()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Purple200),
                ) {
                    Text(text = stringResource(R.string.button_yes), color = White)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        closeDialog()
                    }
                ) {
                    Text(text = stringResource(R.string.button_no), color = Purple200)
                }
            },
            onDismissRequest = { closeDialog() }
        )
    }

}

@Preview
@Composable
private fun DisplayAlertDialogPreview() {
    DisplayAlertDialog(
        "Aviso",
        "Deseja realmente excluir?",
        true,
        {},
        {}
    )
}