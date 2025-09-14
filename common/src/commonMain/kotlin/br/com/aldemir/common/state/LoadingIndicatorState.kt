package br.com.aldemir.common.state

import androidx.compose.runtime.Stable
import br.com.aldemir.common.util.AnimationType
import kotlinx.coroutines.CoroutineScope

@Stable
interface LoadingIndicatorState {
    operator fun get(index: Int): Float

    fun start(animationType: AnimationType, scope: CoroutineScope)
}
