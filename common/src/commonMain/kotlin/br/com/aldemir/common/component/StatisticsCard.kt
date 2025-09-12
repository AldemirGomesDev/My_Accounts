package br.com.aldemir.common.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import br.com.aldemir.common.model.CardState
import br.com.aldemir.common.model.CardType
import br.com.aldemir.common.theme.Shapes
import br.com.aldemir.common.util.emptyFloat
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.common.util.getCurrencySymbol
import br.com.aldemir.common.util.toCurrency
import br.com.aldemir.common.util.toDecimal
import br.com.aldemir.common.R
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
        backgroundColor = MyAccountsTheme.colors.backgroundGreen,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(145.dp)
                .padding(16.dp),
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (totalLabel, totalValue, paidOutLabel, paidOutValue,
                    payableLabel, payableValue, progressValue, iconPaidOut, iconPayable) = createRefs()
                Text(
                    modifier = Modifier
                        .constrainAs(totalLabel) {
                            top.linkTo(parent.top)
                        }
                        .padding(bottom = 20.dp),
                    text = stringResource(Res.string.home_total_month),
                    color = Color.White,
                    style = MyAccountsTheme.typography.paragraph02Bold
                )
                Text(
                    modifier = Modifier
                        .constrainAs(totalValue) {
                            end.linkTo(parent.end)
                        }
                        .padding(bottom = 16.dp),
                    text = cardState.valueTotal.toCurrency(currencySymbol),
                    color = Color.White,
                    style = MyAccountsTheme.typography.paragraph02Normal
                )
                Image(
                    modifier = Modifier.constrainAs(iconPaidOut) {
                        top.linkTo(paidOutLabel.top)
                        bottom.linkTo(paidOutValue.bottom)
                        start.linkTo(parent.start)
                    },
                    painter = painterResource(id = R.drawable.ic_check_circle),
                    contentDescription = emptyString()
                )
                Text(
                    modifier = Modifier
                        .constrainAs(paidOutLabel) {
                            top.linkTo(totalLabel.bottom)
                            start.linkTo(iconPaidOut.end)
                        }
                        .padding(start = 4.dp),
                    text = textChecked,
                    color = Color.White,
                    style = MyAccountsTheme.typography.paragraph02Bold
                )
                Text(
                    modifier = Modifier
                        .constrainAs(paidOutValue) {
                            top.linkTo(paidOutLabel.bottom)
                            start.linkTo(iconPaidOut.end)
                        }
                        .padding(start = 4.dp),
                    text = cardState.paidOut.toCurrency(currencySymbol),
                    color = Color.White,
                    style = MyAccountsTheme.typography.paragraph02Normal
                )
                Image(
                    modifier = Modifier
                        .constrainAs(iconPayable) {
                            top.linkTo(payableLabel.top)
                            bottom.linkTo(payableValue.bottom)
                            start.linkTo(paidOutValue.end)
                        }
                        .padding(start = 24.dp),
                    painter = painterResource(id = R.drawable.ic_report_problem),
                    contentDescription = emptyString()
                )
                Text(
                    modifier = Modifier
                        .constrainAs(payableLabel) {
                            top.linkTo(totalLabel.bottom)
                            start.linkTo(iconPayable.end)
                        }
                        .padding(start = 4.dp),
                    text = textPending,
                    color = Color.White,
                    style = MyAccountsTheme.typography.paragraph02Bold
                )
                Text(
                    modifier = Modifier
                        .constrainAs(payableValue) {
                            top.linkTo(paidOutLabel.bottom)
                            start.linkTo(iconPayable.end)
                        }
                        .padding(start = 4.dp),
                    fontWeight = FontWeight.Normal,
                    text = cardState.pending.toCurrency(currencySymbol),
                    color = Color.White,
                    style = MyAccountsTheme.typography.paragraph02Normal
                )
                LinearProgressIndicatorSample(
                    value = if (!cardState.percentage.isNaN()) {
                        cardState.percentage.toDecimal()
                    } else {
                        emptyFloat()
                    },
                    modifier = Modifier
                        .constrainAs(progressValue) {
                            top.linkTo(payableValue.bottom)
                        }
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(top = 12.dp)
                        .clip(shape = Shapes.large)
                )
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
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