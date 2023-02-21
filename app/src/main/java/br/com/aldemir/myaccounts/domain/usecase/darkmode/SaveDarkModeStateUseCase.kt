package br.com.aldemir.myaccounts.domain.usecase.darkmode


interface SaveDarkModeStateUseCase {
    suspend operator fun invoke(isDarkMode: Boolean)
}