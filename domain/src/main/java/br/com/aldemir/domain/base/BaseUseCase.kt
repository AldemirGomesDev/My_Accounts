package br.com.aldemir.domain.base

import kotlinx.coroutines.CoroutineScope

interface BaseUseCase<in Param, R> {
    suspend fun execute(params: Param): R
    suspend operator fun invoke(
        scope: CoroutineScope,
        params: Param,
    ) = runCatching {
        execute(params)
    }

    @Suppress("UNCHECKED_CAST")
    suspend operator fun invoke(
        scope: CoroutineScope,
    ) {
        invoke(scope, None() as Param)
    }
    class None
}
