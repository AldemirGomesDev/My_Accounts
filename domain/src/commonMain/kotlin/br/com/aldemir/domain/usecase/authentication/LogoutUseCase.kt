package br.com.aldemir.domain.usecase.authentication

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.repository.AuthenticationRepository

class LogoutUseCase(
    private val repository: AuthenticationRepository
) : UseCase<String, Unit> {
    override suspend fun execute(params: String) {
        repository.logout(params)
    }
}