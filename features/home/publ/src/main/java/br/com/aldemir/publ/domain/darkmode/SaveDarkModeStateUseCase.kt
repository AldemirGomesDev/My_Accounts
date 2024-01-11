package br.com.aldemir.publ.domain.darkmode


interface SaveDarkModeStateUseCase {
    suspend operator fun invoke(isDarkMode: Boolean)
}