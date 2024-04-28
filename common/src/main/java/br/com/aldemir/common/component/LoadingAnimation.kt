package br.com.aldemir.common.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.theme.Purple200
import kotlinx.coroutines.delay

@Composable
fun LoadingAnimation(
    circleColor: Color = Purple200,
    circleSize: Dp = 36.dp,
    animationDelay: Int = 400,
    initialAlpha: Float = 0.3f
) {

    val circles = listOf(
        remember { Animatable(initialValue = initialAlpha) },
        remember { Animatable(initialValue = initialAlpha) },
        remember { Animatable(initialValue = initialAlpha) }
    )

    circles.forEachIndexed { index, animatable ->

        LaunchedEffect(Unit) {
            delay(timeMillis = (animationDelay / circles.size).toLong() * index)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = animationDelay
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
    }

    Row(
        modifier = Modifier
    ) {

        circles.forEachIndexed { index, animatable ->
            if (index != 0) {
                Spacer(modifier = Modifier.width(width = 6.dp))
            }
            Box(
                modifier = Modifier
                    .size(size = circleSize)
                    .clip(shape = CircleShape)
                    .background(
                        color = circleColor
                            .copy(alpha = animatable.value)
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingAnimationPreview() {
    LoadingAnimation()
}