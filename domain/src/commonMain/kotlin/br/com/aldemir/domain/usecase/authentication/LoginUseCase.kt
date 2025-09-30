package br.com.aldemir.domain.usecase.authentication

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.UserDomain
import br.com.aldemir.domain.repository.AuthenticationRepository
import com.diamondedge.logging.logging

class LoginUseCase(
    private val authenticationRepository: AuthenticationRepository
) : UseCase<Params, LoginUseCaseState> {
    private val log = logging("TAG_auth")

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

    override fun dispatchErrorResult(throwable: Throwable) {
        super.dispatchErrorResult(throwable)
        log.error { "LoginUseCase-> Error: $throwable"}
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