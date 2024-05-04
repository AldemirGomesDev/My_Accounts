package br.com.aldemir.home.presentation.state

import br.com.aldemir.home.presentation.model.HomeUiModel

sealed class HomeUiState(open val uiModel: HomeUiModel = HomeUiModel()) {
    data object Loading : HomeUiState()
    data class ShowHomeCards(override val uiModel: HomeUiModel) :
        HomeUiState(uiModel)
}