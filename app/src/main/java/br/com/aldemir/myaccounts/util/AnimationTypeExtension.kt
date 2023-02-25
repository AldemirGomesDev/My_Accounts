package br.com.aldemir.myaccounts.util

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import br.com.aldemir.myaccounts.util.Const.BounceAnimationDurationMillis
import br.com.aldemir.myaccounts.util.Const.FadeAnimationDurationMillis
import br.com.aldemir.myaccounts.util.Const.IndicatorSize
import br.com.aldemir.myaccounts.util.Const.NumIndicators

val AnimationType.animationSpec: DurationBasedAnimationSpec<Float>
    get() = when (this) {
        AnimationType.Bounce,
        AnimationType.Fade -> tween(durationMillis = animationDuration)
        AnimationType.LazyBounce -> keyframes {
            durationMillis = animationDuration
            initialValue at 0
            0f at animationDuration / 4
            targetValue / 2f at animationDuration / 2
            targetValue / 2f at animationDuration
        }
    }

val AnimationType.animationDuration: Int
    get() = when (this) {
        AnimationType.Bounce,
        AnimationType.LazyBounce -> BounceAnimationDurationMillis
        AnimationType.Fade -> FadeAnimationDurationMillis
    }

val AnimationType.animationDelay: Int
    get() = animationDuration / NumIndicators

val AnimationType.initialValue: Float
    get() = when (this) {
        AnimationType.Bounce -> IndicatorSize / 2f
        AnimationType.LazyBounce -> -IndicatorSize / 2f
        AnimationType.Fade -> 1f
    }

val AnimationType.targetValue: Float
    get() = when (this) {
        AnimationType.Bounce -> -IndicatorSize / 2f
        AnimationType.LazyBounce -> IndicatorSize / 2f
        AnimationType.Fade -> .2f
    }