package br.com.aldemir.domain.usecase.authentication

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.UserDomain
import br.com.aldemir.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetLoggerUserUseCase(
    private val repository: AuthenticationRepository
) : UseCase<UseCase.None, Flow<GetLoggerUserState>> {
    override suspend fun execute(params: UseCase.None): Flow<GetLoggerUserState> {
        return repository.getLoggedUser().map { loggedUser ->
            if (loggedUser != null) {
                GetLoggerUserState.LoggedUser(loggedUser)
            } else {
                GetLoggerUserState.NotLoggedUser
            }
        }
    }
}

sealed class GetLoggerUserState {
    data class LoggedUser(val userDomain: UserDomain) : GetLoggerUserState()
    data object NotLoggedUser : GetLoggerUserState()
}