package br.com.aldemir.common.model

import br.com.aldemir.common.util.emptyString
import kotlinx.serialization.Serializable

@Serializable
data class UserLogged(
    val id: Int = 0,
    val name: String = emptyString(),
    val userName: String = emptyString(),
    val password: String = emptyString(),
)
