package br.com.aldemir.data.mapper

import br.com.aldemir.data.database.model.UserDTO
import br.com.aldemir.domain.model.UserDomain


fun UserDomain.toDto() = UserDTO(
    id = id,
    name = name,
    userName = userName,
    password = password,
    isLogged = isLogged
)

fun UserDTO.toDomain() = UserDomain(
    id = id,
    name = name,
    userName = userName,
    password = password,
    isLogged = isLogged
)