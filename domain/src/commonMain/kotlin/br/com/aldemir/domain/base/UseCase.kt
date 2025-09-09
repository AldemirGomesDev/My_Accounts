package br.com.aldemir.domain.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface UseCase<in Param, R> {
    suspend fun execute(params: Param): R

    /**
     * Chamado quando houver erro no invoke.
     * Pode ser sobreescrito pelos UseCases concretos para enviar logs/monitoria.
     */
    fun dispatchErrorResult(throwable: Throwable) {
        // Implementação default: não faz nada
    }

    operator fun invoke(
        scope: CoroutineScope,
        params: Param,
        block: UseCaseResult<R>.() -> Unit
    ) {
        scope.launch {
            val result = runCatching { execute(params) }
            val handler = UseCaseResult<R>().apply(block)

            result.fold(
                onSuccess = { handler.success?.invoke(it) },
                onFailure = { throwable ->
                    dispatchErrorResult(throwable) // ✅ hook para log/monitoria
                    handler.error?.invoke(throwable)
                }
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    operator fun invoke(
        scope: CoroutineScope,
        block: UseCaseResult<R>.() -> Unit
    ) {
        invoke(scope, None() as Param, block)
    }

    class None
}