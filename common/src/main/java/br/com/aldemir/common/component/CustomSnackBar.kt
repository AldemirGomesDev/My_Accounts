package br.com.aldemir.common.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.theme.GreenLight
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import br.com.aldemir.common.theme.RedErrorLight
import br.com.aldemir.common.theme.White

@Composable
fun CustomSnackBar(
    snackBarState: SnackBarState,
    message: String,
    isRtl: Boolean = false,
    offsetY: Dp = 0.dp
) {
    Snackbar(
        modifier = Modifier.offset(y = offsetY),
        backgroundColor = snackBarState.backgroundColor(),
        contentColor = snackBarState.contentColor
    ) {
        CompositionLocalProvider(
            LocalLayoutDirection provides
                    if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
        ) {
            Row(
                modifier = Modifier
                    .background(color = snackBarState.backgroundColor())
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = snackBarState.imageVector,
                    modifier = Modifier.padding(end = MyAccountsTheme.dimensions.padding16),
                    contentDescription = null,
                    tint = snackBarState.tintIcon
                )
                Text(
                    style = MyAccountsTheme.typography.subTitleBold,
                    text = message
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun CustomSnackBarPreview() {
    MyAccountsTheme {
        CustomSnackBar(
            snackBarState = SnackBarState.SUCCESS,
            message = "Salvo com Sucesso!"
        )
    }
}

@PreviewLightDark
@Composable
fun CustomSnackBarPreview2() {
    MyAccountsTheme {
        CustomSnackBar(
            snackBarState = SnackBarState.ERROR,
            message = "Erro ao salvar!"
        )
    }
}

enum class SnackBarState(
    val imageVector: ImageVector,
    val backgroundColor: @Composable () -> Color,
    val contentColor: Color = GreenLight,
    val tintIcon: Color = GreenLight,
) {

    SUCCESS(
        imageVector = Icons.Default.Check,
        backgroundColor = { MyAccountsTheme.colors.success},
        contentColor = White,
        tintIcon = White,
    ),
    ALERT(
        imageVector = Icons.Default.Info,
        backgroundColor = { White },
        contentColor = GreenLight,
        tintIcon = GreenLight,
    ),
    ERROR(
        imageVector = Icons.Filled.Close,
        backgroundColor = { MyAccountsTheme.colors.error },
        contentColor = White,
        tintIcon = White,
    ),
    NONE(
        imageVector = Icons.Default.Close,
        backgroundColor = { White },
        contentColor = RedErrorLight,
        tintIcon = GreenLight,
    )
}
