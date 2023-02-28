package br.com.aldemir.myaccounts.presentation.component

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
import androidx.compose.ui.unit.dp
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.presentation.home.model.HomeCardData
import br.com.aldemir.myaccounts.presentation.theme.*
import br.com.aldemir.myaccounts.util.*

@Composable
fun HomeCard(
    homeCardData: HomeCardData,
) {
    Card(
        shape = Shapes.large,
        backgroundColor = GreenDark,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = MEDIUM_PADDING),
                    text = stringResource(id = R.string.resume_monthly),
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
                    Column(modifier = Modifier.padding(start = MEDIUM_PADDING)) {
                        Text(
                            modifier = Modifier
                                .padding(start = 4.dp),
                            fontWeight = FontWeight.Bold,
                            text = stringResource(id = R.string.add_revenue),
                            color = Color.White
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 4.dp),
                            fontWeight = FontWeight.Normal,
                            text = homeCardData.valueRecipe.toCurrency(),
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
                            .padding(start = 24.dp),
                        painter = painterResource(id = R.drawable.ic_report_problem),
                        contentDescription = emptyString(),
                        colorFilter = ColorFilter.tint(color = MediumPriorityColor)
                    )
                    Column(modifier = Modifier.padding(start = MEDIUM_PADDING)) {
                        Text(
                            modifier = Modifier
                                .padding(start = 4.dp),
                            fontWeight = FontWeight.Bold,
                            text = stringResource(id = R.string.add_expense),
                            color = Color.White
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 4.dp),
                            fontWeight = FontWeight.Normal,
                            text = homeCardData.valueExpense.toCurrency(),
                            color = MediumPriorityColor
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = LARGE_PADDING),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp),
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.home_balance),
                    color = Color.White
                )
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp),
                    fontWeight = FontWeight.Bold,
                    text = homeCardData.valueBalance.toCurrency(),
                    color = if (homeCardData.valueBalance >= 0) LowPriorityColor else MediumPriorityColor
                )
            }
        }
    }
}