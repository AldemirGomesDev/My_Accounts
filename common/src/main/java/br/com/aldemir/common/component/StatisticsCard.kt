package br.com.aldemir.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import br.com.aldemir.common.model.CardState
import br.com.aldemir.common.model.CardType
import br.com.aldemir.common.theme.GreenDark
import br.com.aldemir.common.theme.Shapes
import br.com.aldemir.common.util.emptyFloat
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.common.util.getCurrencySymbol
import br.com.aldemir.common.util.toCurrency
import br.com.aldemir.common.util.toDecimal
import br.com.aldemir.common.util.*
import br.com.aldemir.common.R

@Composable
fun StatisticsCard(
    cardState: CardState,
) {
    val textChecked: String = when (cardState.cardType) {
        CardType.EXPENSE -> stringResource(id = R.string.expense_paid_out)
        CardType.RECIPE -> stringResource(id = R.string.recipe_checked)
        CardType.HOME -> stringResource(id = R.string.recipe_card_title)
    }
    val textPending: String = when (cardState.cardType) {
        CardType.EXPENSE ->stringResource(id = R.string.expense_to_pay)
        CardType.RECIPE -> stringResource(id = R.string.recipe_pending)
        CardType.HOME -> stringResource(id = R.string.add_expense)
    }

    val currentLocal = Locale.current
    val currencySymbol = getCurrencySymbol(currentLocal.language, currentLocal.region)

    Card(
        shape = Shapes.large,
        backgroundColor = GreenDark,
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
                    text = stringResource(id = R.string.home_total_month),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    modifier = Modifier
                        .constrainAs(totalValue) {
                            end.linkTo(parent.end)
                        }
                        .padding(bottom = 16.dp),
                    fontWeight = FontWeight.Bold,
                    text = cardState.valueTotal.toCurrency(currencySymbol),
                    color = Color.White
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
                    fontWeight = FontWeight.Bold,
                    text = textChecked,
                    color = Color.White
                )
                Text(
                    modifier = Modifier
                        .constrainAs(paidOutValue) {
                            top.linkTo(paidOutLabel.bottom)
                            start.linkTo(iconPaidOut.end)
                        }
                        .padding(start = 4.dp),
                    fontWeight = FontWeight.Normal,
                    text = cardState.paidOut.toCurrency(currencySymbol),
                    color = Color.White
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
                    fontWeight = FontWeight.Bold,
                    text = textPending,
                    color = Color.White
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
                    color = Color.White
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