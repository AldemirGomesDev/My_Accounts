package br.com.aldemir.domain.usecase.authentication

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.UserDomain
import br.com.aldemir.domain.repository.AuthenticationRepository

class LoginUseCase(
    private val authenticationRepository: AuthenticationRepository
) : BaseUseCase<Params, LoginUseCaseState> {
    override suspend fun execute(params: Params): LoginUseCaseState {
        return checkUser(authenticationRepository.login(params.userName, params.password))
    }

    private fun checkUser(userDomain: UserDomain?): LoginUseCaseState {
        return if (userDomain != null) {
            LoginUseCaseState.Success(userDomain)
        } else {
            LoginUseCaseState.NotFound
        }
    }
}

data class Params(
    val userName: String,
    val password: String
)

sealed class LoginUseCaseState {
    data class Success(val userDomain: UserDomain) : LoginUseCaseState()
    data object NotFound : LoginUseCaseState()
}