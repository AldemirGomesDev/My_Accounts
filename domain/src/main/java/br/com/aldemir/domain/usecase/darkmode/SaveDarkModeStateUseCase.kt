package br.com.aldemir.domain.usecase.darkmode


interface SaveDarkModeStateUseCase {
    suspend operator fun invoke(isDarkMode: Boolean)
}