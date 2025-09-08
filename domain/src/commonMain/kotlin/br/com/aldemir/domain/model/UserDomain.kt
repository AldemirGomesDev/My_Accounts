package br.com.aldemir.domain.model

data class UserDomain(
    val id: Int = 0,
    val name: String,
    val userName: String,
    val password: String,
    val isLogged: Boolean
)
