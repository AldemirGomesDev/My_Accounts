package br.com.aldemir.navigation.state

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.aldemir.common.R

data class TopBarState(
    @StringRes val titleResId: Int = R.string.expense_list,
    val imageIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    val onClick: () -> Unit,
    val isHome: Boolean = false,
    val isVisible: Boolean = false
)
