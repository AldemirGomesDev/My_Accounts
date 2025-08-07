package br.com.aldemir.authentication.data

data class DialogModel(
    val title: String,
    val subtitle: String,
    val description: String,
    val positiveButtonText: String? = null,
    val negativeButtonText: String,
)
