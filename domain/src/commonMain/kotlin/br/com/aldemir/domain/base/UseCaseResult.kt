package br.com.aldemir.domain.base

/**
 * DSL que permite configurar callbacks de sucesso e erro
 */
class UseCaseResult<R> {
    var success: ((R) -> Unit)? = null
    var error: ((Throwable) -> Unit)? = null
}
