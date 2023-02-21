package br.com.aldemir.myaccounts.domain.usecase.darkmode

import kotlinx.coroutines.flow.Flow

interface ReadDarkModeStateUseCase {
    suspend operator fun invoke(): Flow<Boolean>
}