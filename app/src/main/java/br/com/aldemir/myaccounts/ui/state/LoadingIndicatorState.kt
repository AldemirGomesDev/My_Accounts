package br.com.aldemir.myaccounts.ui.state

import androidx.compose.runtime.Stable
import br.com.aldemir.myaccounts.util.AnimationType
import kotlinx.coroutines.CoroutineScope

@Stable
interface LoadingIndicatorState {
    operator fun get(index: Int): Float

    fun start(animationType: AnimationType, scope: CoroutineScope)
}
