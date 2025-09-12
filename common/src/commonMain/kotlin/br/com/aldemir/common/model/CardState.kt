package br.com.aldemir.common.model

data class CardState(
    var valueTotal: Double = 0.0,
    var paidOut: Double = 0.0,
    var pending: Double = 0.0,
    var percentage: Float = 0F,
    var cardType: CardType = CardType.EXPENSE
)
