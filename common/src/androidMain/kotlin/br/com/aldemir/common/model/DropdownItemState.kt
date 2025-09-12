package br.com.aldemir.common.model

import androidx.compose.ui.graphics.vector.ImageVector

data class DropdownItemState(
    val type: DropdownItemType,
    val titleRes: Int,
    val icon: ImageVector,
)
