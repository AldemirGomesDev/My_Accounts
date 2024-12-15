package br.com.aldemir.domain.usecase.authentication

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.UserDomain
import br.com.aldemir.domain.repository.AuthenticationRepository

class LoginUseCase(
    private val authenticationRepository: AuthenticationRepository
) : BaseUseCase<Params, UserDomain?> {
    override suspend fun execute(params: Params): UserDomain? {
        return authenticationRepository.login(params.userName, params.password)
    }
}

data class Params(
    val userName: String,
    val password: String
)