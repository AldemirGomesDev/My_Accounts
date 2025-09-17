package br.com.aldemir.common.model

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource

data class DropdownItemState(
    val type: DropdownItemType,
    val titleRes: StringResource,
    val icon: ImageVector,
)
