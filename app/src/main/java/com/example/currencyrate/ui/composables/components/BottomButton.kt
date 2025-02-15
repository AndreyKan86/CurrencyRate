package com.example.currencyrate.ui.composables.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyrate.ui.theme.PROCEED_BUTTON_TEXT
import com.example.currencyrate.ui.theme.modifiers.buttomStart
import com.example.currencyrate.ui.theme.styles.StartButtomStyle
import com.example.currencyrate.ui.viewmodels.CurrencyViewModel

//Нижняя кнопка
@Composable
fun BottomButton(currencyViewModel: CurrencyViewModel = viewModel())
{
    Button(onClick = {
        currencyViewModel.loadCurrencyRates()
    },
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray) ) {
        Text( PROCEED_BUTTON_TEXT,
            modifier = Modifier.buttomStart(),
            style = StartButtomStyle
        )
    }
}