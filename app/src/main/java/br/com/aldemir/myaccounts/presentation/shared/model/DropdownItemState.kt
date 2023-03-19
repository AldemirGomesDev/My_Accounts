package br.com.aldemir.myaccounts.presentation.shared.model

import androidx.compose.ui.graphics.vector.ImageVector

data class DropdownItemState(
    val type: DropdownItemType,
    val titleRes: Int,
    val icon: ImageVector
)
