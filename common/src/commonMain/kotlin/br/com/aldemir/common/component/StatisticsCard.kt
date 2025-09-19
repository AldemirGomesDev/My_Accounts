package br.com.aldemir.common.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import br.com.aldemir.common.model.CardState
import br.com.aldemir.common.model.CardType
import br.com.aldemir.common.theme.Shapes
import br.com.aldemir.common.util.emptyFloat
import br.com.aldemir.common.util.getCurrencySymbol
import br.com.aldemir.common.util.toCurrency
import br.com.aldemir.common.util.toDecimal
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.home_add_expense
import myaccounts.common.generated.resources.home_expense_paid_out
import myaccounts.common.generated.resources.home_expense_to_pay
import myaccounts.common.generated.resources.home_recipe_card_title
import myaccounts.common.generated.resources.home_recipe_checked
import myaccounts.common.generated.resources.home_recipe_pending
import myaccounts.common.generated.resources.home_total_month
import org.jetbrains.compose.resources.stringResource

@Composable
fun StatisticsCard(
    cardState: CardState,
) {
    val textChecked: String = when (cardState.cardType) {
        CardType.EXPENSE -> stringResource(Res.string.home_expense_paid_out)
        CardType.RECIPE -> stringResource(Res.string.home_recipe_checked)
        CardType.HOME -> stringResource(Res.string.home_recipe_card_title)
    }
    val textPending: String = when (cardState.cardType) {
        CardType.EXPENSE ->stringResource(Res.string.home_expense_to_pay)
        CardType.RECIPE -> stringResource(Res.string.home_recipe_pending)
        CardType.HOME -> stringResource(Res.string.home_add_expense)
    }

    val currentLocal = Locale.current
    val currencySymbol = getCurrencySymbol(currentLocal.language, currentLocal.region)

    Card(
        shape = Shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MyAccountsTheme.colors.backgroundGreen
        ),
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(145.dp)
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 20.dp),
                    text = stringResource(Res.string.home_total_month),
                    color = Color.White,
                    style = MyAccountsTheme.typography.paragraph02Bold
                )
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = cardState.valueTotal.toCurrency(currencySymbol),
                    color = Color.White,
                    style = MyAccountsTheme.typography.paragraph02Normal
                )
            }
            Row {
                Row {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        tint = MyAccountsTheme.colors.success,
                        contentDescription = null
                    )
                    Column {
                        Text(
                            modifier = Modifier.padding(start = 4.dp),
                            text = textChecked,
                            color = Color.White,
                            style = MyAccountsTheme.typography.paragraph02Bold
                        )
                        Text(
                            modifier = Modifier.padding(start = 4.dp),
                            text = cardState.paidOut.toCurrency(currencySymbol),
                            color = Color.White,
                            style = MyAccountsTheme.typography.paragraph02Normal
                        )
                    }
                }
                Row {
                    Icon(
                        modifier = Modifier .padding(start = 24.dp),
                        imageVector = Icons.Filled.Warning,
                        tint = MyAccountsTheme.colors.warning,
                        contentDescription = null
                    )
                    Column {
                        Text(
                            modifier = Modifier .padding(start = 4.dp),
                            text = textPending,
                            color = Color.White,
                            style = MyAccountsTheme.typography.paragraph02Bold
                        )
                        Text(
                            modifier = Modifier .padding(start = 4.dp),
                            fontWeight = FontWeight.Normal,
                            text = cardState.pending.toCurrency(currencySymbol),
                            color = Color.White,
                            style = MyAccountsTheme.typography.paragraph02Normal
                        )
                    }
                }
            }
            LinearProgressIndicatorSample(
                value = if (!cardState.percentage.isNaN()) {
                    cardState.percentage.toDecimal()
                } else {
                    emptyFloat()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(top = 12.dp)
                    .clip(shape = Shapes.large)
            )
        }
    }
}

@Composable
private fun StatisticsCardPreview() {
    MyAccountsTheme {
        StatisticsCard(
            cardState = CardState(
                valueTotal = 1000.0,
                paidOut = 500.0,
                pending = 500.0,
                percentage = 50.0F,
                cardType = CardType.HOME
            )
        )
    }
}