package br.com.aldemir.navigation.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.graphics.vector.ImageVector
import myaccounts.navigation.generated.resources.Res
import myaccounts.navigation.generated.resources.expense_list_screen_title
import org.jetbrains.compose.resources.StringResource

data class TopBarState(
    val titleResId: StringResource = Res.string.expense_list_screen_title,
    val imageIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    val onClick: () -> Unit,
    val isHome: Boolean = false,
    val isVisible: Boolean = false
)
