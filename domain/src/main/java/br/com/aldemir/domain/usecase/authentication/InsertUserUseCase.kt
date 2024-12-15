package br.com.aldemir.domain.usecase.authentication

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.UserDomain
import br.com.aldemir.domain.repository.AuthenticationRepository

class InsertUserUseCase(
    private val authenticationRepository: AuthenticationRepository
) : BaseUseCase<UserDomain, Long> {
    override suspend fun execute(params: UserDomain): Long {
        return authenticationRepository.insert(params)
    }
}