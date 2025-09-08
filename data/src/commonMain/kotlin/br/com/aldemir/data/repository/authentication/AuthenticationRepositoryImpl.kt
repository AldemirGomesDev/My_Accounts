package br.com.aldemir.data.repository.authentication

import br.com.aldemir.data.database.room.authentication.AuthenticationDao
import br.com.aldemir.data.mapper.toDomain
import br.com.aldemir.data.mapper.toDto
import br.com.aldemir.domain.model.UserDomain
import br.com.aldemir.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val authenticationDao: AuthenticationDao
): AuthenticationRepository {
    override suspend fun insert(userDomain: UserDomain): Long {
        return authenticationDao.insert(userDomain.toDto())
    }

    override suspend fun update(userDomain: UserDomain): Int {
        return authenticationDao.update(userDomain.toDto())
    }

    override suspend fun delete(userDomain: UserDomain): Int {
        return authenticationDao.delete(userDomain.toDto())
    }

    override suspend fun login(userName: String, password: String): UserDomain? {
        return authenticationDao.login(userName, password)?.toDomain()
    }

    override suspend fun getUser(userName: String, password: String): UserDomain? {
        return null
    }

    override suspend fun setLoggedIn(userId: Int, isLogged: Boolean): Int {
        return 0
    }

    override suspend fun isLogged(userName: String): Boolean {
        return authenticationDao.isLogged(userName)
    }

    override suspend fun logout(userName: String): Int {
        return authenticationDao.logout(userName)
    }
}