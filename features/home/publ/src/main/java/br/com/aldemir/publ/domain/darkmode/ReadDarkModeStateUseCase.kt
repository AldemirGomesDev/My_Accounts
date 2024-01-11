package br.com.aldemir.publ.domain.darkmode

import kotlinx.coroutines.flow.Flow

interface ReadDarkModeStateUseCase {
    suspend operator fun invoke(): Flow<Boolean>
}