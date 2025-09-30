package br.com.aldemir.data.repository.authentication

import br.com.aldemir.data.database.room.authentication.AuthenticationDao
import br.com.aldemir.data.mapper.toDomain
import br.com.aldemir.data.mapper.toDto
import br.com.aldemir.domain.model.UserDomain
import br.com.aldemir.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val dao: AuthenticationDao
): AuthenticationRepository {
    override suspend fun insert(userDomain: UserDomain): Long {
        return dao.insert(userDomain.toDto())
    }

    override suspend fun update(userDomain: UserDomain): Int {
        return dao.update(userDomain.toDto())
    }

    override suspend fun delete(userDomain: UserDomain): Int {
        return dao.delete(userDomain.toDto())
    }

    override suspend fun login(userName: String, password: String): UserDomain? {
        val user = dao.getUser(userName, password) ?: return null
        dao.getAllUsers().forEach {
            dao.setLoggedIn(it.id, false)
        }
        dao.setLoggedIn(user.id, true)
        return user.toDomain()
    }

    override suspend fun getUser(userName: String, password: String): UserDomain? {
        return null
    }

    override suspend fun setLoggedIn(userId: Int, isLogged: Boolean): Int {
        return 0
    }

    override suspend fun isLogged(userName: String): Boolean {
        return dao.isLogged(userName)
    }

    override suspend fun logout(userName: String): Int {
        return dao.logout(userName)
    }

    override suspend fun getAllUsers(): List<UserDomain> {
        return dao.getAllUsers().map {
            it.toDomain()
        }
    }

    override suspend fun getLoggedUser(): UserDomain? {
        return dao.getAllUsers().find { it.isLogged }?.toDomain()
    }
}