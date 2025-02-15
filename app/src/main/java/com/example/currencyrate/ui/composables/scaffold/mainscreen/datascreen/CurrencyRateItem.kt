package com.example.currencyrate.ui.composables.scaffold.mainscreen.datascreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.currencyrate.ui.data.CurrencyRate
import com.example.currencyrate.ui.theme.DATE_LABEL
import com.example.currencyrate.ui.theme.RATE_LABEL
import com.example.currencyrate.ui.theme.styles.DateTextStyle

//Элементы списка
@Composable
fun CurrencyRateItem(rate: CurrencyRate) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(text = DATE_LABEL + rate.date, style = DateTextStyle)
        Text(text = RATE_LABEL + rate.value, style = DateTextStyle)
    }
}