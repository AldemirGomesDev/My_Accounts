package br.com.aldemir.domain.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface UseCase<in Param, R> {
    suspend fun execute(params: Param): R

    fun dispatchErrorResult(throwable: Throwable) { }

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
                    dispatchErrorResult(throwable)
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

//suspend fun <P, R> UseCase<P, R>.awaitForResult(params: P): R =
//    suspendCancellableCoroutine { cont ->
//        // Usa o escopo da própria coroutine corrente
//        this@awaitForResult(cont.context + SupervisorJob()).invoke(
//            CoroutineScope(cont.context),
//            params
//        ) {
//            success = { result ->
//                if (cont.isActive) cont.resume(result)
//            }
//            error = { throwable ->
//                if (cont.isActive) cont.resumeWithException(throwable)
//            }
//        }
//    }

suspend fun <P, R> UseCase<P, R>.awaitForResult(params: P): R =
    suspendCancellableCoroutine { cont ->
        // Cria um escopo com o mesmo contexto da coroutine atual
        val scope = CoroutineScope(cont.context)

        this@awaitForResult(scope, params) {
            success = { result ->
                if (cont.isActive) cont.resume(result)
            }
            error = { throwable ->
                if (cont.isActive) cont.resumeWithException(throwable)
            }
        }
    }

//suspend fun <P, R> UseCase<P, R>.awaitForResult(scope: CoroutineScope, params: P): R =
//    suspendCancellableCoroutine { cont ->
//        scope.launch {
//            this@awaitForResult(this, params) {
//                success = { result ->
//                    if (cont.isActive) cont.resume(result)
//                }
//                error = { throwable ->
//                    if (cont.isActive) cont.resumeWithException(throwable)
//                }
//            }
//        }
//    }