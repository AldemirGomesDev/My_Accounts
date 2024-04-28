package br.com.aldemir.common.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.aldemir.common.theme.LowPriorityColor
import br.com.aldemir.common.theme.MediumPriorityColor
import br.com.aldemir.common.util.emptyString

@Composable
fun LinearProgressIndicatorSample(
    value: Float,
    modifier: Modifier = Modifier
) {
    val animatedProgress = animateFloatAsState(
        targetValue = value,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec, label = emptyString()
    ).value

    Box(modifier = modifier) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            progress = animatedProgress,
            color = LowPriorityColor,
            backgroundColor = MediumPriorityColor
        )
    }
}

@Preview
@Composable
fun LinearProgressIndicatorSamplePreview() {
    LinearProgressIndicatorSample(20f)
}