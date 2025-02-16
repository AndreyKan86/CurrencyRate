package com.example.currencyrate.ui.composables.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyrate.ui.theme.PROCEED_BUTTON_TEXT
import com.example.currencyrate.ui.theme.styles.StartButtomStyle
import com.example.currencyrate.ui.viewmodels.CurrencyViewModel

//Нижняя кнопка
@Composable
fun BottomButton(currencyViewModel: CurrencyViewModel = viewModel())
{
    Button(onClick = {
        currencyViewModel.loadCurrencyRates()
    },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary) ) {
        Text( PROCEED_BUTTON_TEXT,
            modifier = Modifier.padding(2.dp),
            style = StartButtomStyle
        )
    }
}