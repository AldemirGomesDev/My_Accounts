package br.com.aldemir.myaccounts

import br.com.aldemir.common.model.UserLogged
import br.com.aldemir.domain.model.UserDomain

fun UserDomain.toUserLogger() = UserLogged(
    id = id,
    name = name,
    userName = userName,
    password = password,
)