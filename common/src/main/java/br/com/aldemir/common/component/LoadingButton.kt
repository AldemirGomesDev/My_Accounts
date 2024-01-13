package br.com.aldemir.common.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import br.com.aldemir.common.theme.FONT_SIZE_16
import br.com.aldemir.common.theme.LARGEST_PADDING
import br.com.aldemir.common.theme.MEDIUM_PADDING
import br.com.aldemir.common.theme.MarginSingle
import br.com.aldemir.common.theme.Purple200
import br.com.aldemir.common.theme.Shapes
import br.com.aldemir.common.R
import br.com.aldemir.common.util.AnimationType
import br.com.aldemir.common.util.emptyString


@Composable
fun LoadingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    loading: Boolean = false,
    animationType: AnimationType = AnimationType.Bounce,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    indicatorSpacing: Dp = MarginSingle,
    content: @Composable () -> Unit,
) {
    val contentAlpha by animateFloatAsState(targetValue = if (loading) 0f else 1f, label = emptyString())
    val loadingAlpha by animateFloatAsState(targetValue = if (loading) 1f else 0f, label = emptyString())
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = PaddingValues(
            start = LARGEST_PADDING,
            top = MEDIUM_PADDING,
            end = LARGEST_PADDING,
            bottom = MEDIUM_PADDING
        ),
        shape = Shapes.large,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            LoadingIndicator(
                animating = loading,
                modifier = Modifier.graphicsLayer { alpha = loadingAlpha },
                color = colors.contentColor(enabled = enabled).value,
                indicatorSpacing = indicatorSpacing,
                animationType = animationType,
            )
            Box(
                modifier = Modifier.graphicsLayer { alpha = contentAlpha }
            ) {
                content()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingButtonPreview() {
    LoadingButton(
        enabled = true,
        colors = ButtonDefaults.buttonColors(backgroundColor = Purple200),
        onClick = {}
    ) {
        Text(
            color = Color.White,
            text = stringResource(id = R.string.button_add_text),
            fontSize = FONT_SIZE_16,
        )
    }
}