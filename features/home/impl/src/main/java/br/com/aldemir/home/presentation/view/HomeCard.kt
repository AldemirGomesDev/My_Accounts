package br.com.aldemir.home.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import br.com.aldemir.home.presentation.model.HomeCardData
import br.com.aldemir.common.theme.*
import br.com.aldemir.common.R
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.common.util.getCurrencySymbol
import br.com.aldemir.common.util.toCurrency

@Composable
fun HomeCard(
    homeCardData: HomeCardData,
) {
    val currentLocal = Locale.current
    val currencySymbol = getCurrencySymbol(currentLocal.language, currentLocal.region)
    Card(
        shape = Shapes.large,
        backgroundColor = MyAccountsTheme.colors.backgroundGreen,
        modifier = Modifier.padding(vertical = MyAccountsTheme.dimensions.padding16)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MyAccountsTheme.dimensions.padding16),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = MyAccountsTheme.dimensions.padding8),
                    text = stringResource(id = R.string.account_resume_monthly),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier,
                        painter = painterResource(id = R.drawable.ic_check_circle),
                        contentDescription = emptyString()
                    )
                    Column(modifier = Modifier.padding(start = MyAccountsTheme.dimensions.padding8)) {
                        Text(
                            modifier = Modifier
                                .padding(start = MyAccountsTheme.dimensions.padding4),
                            fontWeight = FontWeight.Bold,
                            text = stringResource(id = R.string.recipe_card_title),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(MyAccountsTheme.dimensions.sizing2))
                        Text(
                            modifier = Modifier
                                .padding(start = MyAccountsTheme.dimensions.padding4),
                            fontWeight = FontWeight.Normal,
                            text = homeCardData.valueRecipe.toCurrency(currencySymbol),
                            color = LowPriorityColor
                        )
                    }
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .padding(start = MyAccountsTheme.dimensions.padding24),
                        painter = painterResource(id = R.drawable.ic_report_problem),
                        contentDescription = emptyString(),
                        colorFilter = ColorFilter.tint(color = MediumPriorityColor)
                    )
                    Column(
                        modifier = Modifier
                            .padding(start = MyAccountsTheme.dimensions.padding8)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    start = MyAccountsTheme.dimensions.padding4,
                                ),
                            fontWeight = FontWeight.Bold,
                            text = stringResource(id = R.string.add_expense),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(MyAccountsTheme.dimensions.sizing2))
                        Text(
                            modifier = Modifier
                                .padding(start = MyAccountsTheme.dimensions.padding4),
                            fontWeight = FontWeight.Normal,
                            text = homeCardData.valueExpense.toCurrency(currencySymbol),
                            color = MediumPriorityColor
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MyAccountsTheme.dimensions.padding12),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = MyAccountsTheme.dimensions.padding4),
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.home_balance),
                    color = Color.White
                )
                Text(
                    modifier = Modifier
                        .padding(start = MyAccountsTheme.dimensions.padding4),
                    fontWeight = FontWeight.Bold,
                    text = homeCardData.valueBalance.toCurrency(currencySymbol),
                    color = if (homeCardData.valueBalance >= 0) LowPriorityColor else MediumPriorityColor
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeCardPreview() {
    HomeCard(homeCardData = HomeCardData())
}