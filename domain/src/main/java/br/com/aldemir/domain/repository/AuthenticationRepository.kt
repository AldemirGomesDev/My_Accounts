package br.com.aldemir.domain.repository

import br.com.aldemir.domain.model.UserDomain

interface AuthenticationRepository {
    suspend fun insert(userDomain: UserDomain): Long
    suspend fun update(userDomain: UserDomain): Int
    suspend fun delete(userDomain: UserDomain): Int
    suspend fun login(userName: String, password: String): UserDomain?
    suspend fun getUser(userName: String, password: String): UserDomain?
    suspend fun setLoggedIn(userId: Int, isLogged: Boolean): Int
    suspend fun isLogged(userName: String): Boolean
    suspend fun logout(userName: String): Int
}