package br.com.aldemir.myaccounts.presentation.home.model

import br.com.aldemir.myaccounts.util.emptyString

data class MonthValue(
    var month: String = emptyString(),
    var value: Double = 0.0
)
