package br.com.aldemir.domain.usecase.authentication

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.UserDomain
import br.com.aldemir.domain.repository.AuthenticationRepository

class GetLoggerUserUseCase(
    private val repository: AuthenticationRepository
) : UseCase<UseCase.None, GetLoggerUserState> {
    override suspend fun execute(params: UseCase.None): GetLoggerUserState {
        val loggedUser = repository.getLoggedUser()
        return if (loggedUser != null) {
            GetLoggerUserState.LoggedUser(loggedUser)
        } else {
            GetLoggerUserState.NotLoggedUser
        }
    }
}

sealed class GetLoggerUserState {
    data class LoggedUser(val userDomain: UserDomain) : GetLoggerUserState()
    data object NotLoggedUser : GetLoggerUserState()
}