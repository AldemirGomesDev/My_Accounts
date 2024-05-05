package br.com.aldemir.common.presentation.splash


import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.R
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import br.com.aldemir.common.util.Const.SPLASH_SCREEN_DELAY
import br.com.aldemir.common.util.emptyString
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    isDarkTheme: Boolean,
    navigateToListScreen: () -> Unit,
) {
    var startAnimation by remember { mutableStateOf(false) }
    val offsetState by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 100.dp,
        animationSpec = tween(
            durationMillis = 1000
        ), label = emptyString()
    )
    val alphaState by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        ), label = emptyString()
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(SPLASH_SCREEN_DELAY)
        navigateToListScreen()
    }

    Splash(isDarkTheme = isDarkTheme, offsetState = offsetState, alphaState = alphaState)
}

@Composable
fun Splash(
    isDarkTheme: Boolean,
    offsetState: Dp, alphaState: Float
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MyAccountsTheme.colors.backgroundGreen),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(MyAccountsTheme.dimensions.sizing120)
                .offset(y = offsetState)
                .alpha(alpha = alphaState),
            painter = painterResource(id = getLogo(isDarkTheme)),
            contentDescription = stringResource(id = R.string.account_logo)
        )
    }
}

@Composable
private fun getLogo(isDarkTheme: Boolean): Int {
    return if (isDarkTheme) {
        R.drawable.icon_despesa_light
    } else {
        R.drawable.icon_despesa
    }
}

@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
private fun SplashScreenPreview() {
    MyAccountsTheme {
        Surface {
            SplashScreen(
                isDarkTheme = true,
                navigateToListScreen = {}
            )
        }
    }
}