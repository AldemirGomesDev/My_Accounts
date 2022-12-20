package br.com.aldemir.myaccounts.presentation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import br.com.aldemir.myaccounts.presentation.theme.LARGEST_PADDING
import br.com.aldemir.myaccounts.presentation.theme.MEDIUM_PADDING
import br.com.aldemir.myaccounts.presentation.theme.MarginSingle
import br.com.aldemir.myaccounts.presentation.theme.Shapes
import br.com.aldemir.myaccounts.util.AnimationType


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
    val contentAlpha by animateFloatAsState(targetValue = if (loading) 0f else 1f)
    val loadingAlpha by animateFloatAsState(targetValue = if (loading) 1f else 0f)
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