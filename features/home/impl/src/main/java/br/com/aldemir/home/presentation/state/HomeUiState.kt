package br.com.aldemir.home.presentation.state

import br.com.aldemir.home.presentation.model.HomeUiModel

sealed class HomeUiState(open val uiModel: HomeUiModel = HomeUiModel()) {
    object Initial : HomeUiState()
    object ShowRecipeEmpty : HomeUiState()
    object ShowExpenseEmpty : HomeUiState()
    data class ShowHomeCard(override val uiModel: HomeUiModel) :
        HomeUiState(uiModel)

    data class ShowHomeRecipeBar(override val uiModel: HomeUiModel) :
        HomeUiState(uiModel)

    data class ShowHomeExpenseBar(override val uiModel: HomeUiModel) :
        HomeUiState(uiModel)
}