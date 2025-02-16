package com.example.currencyrate.ui.composables.components

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyrate.ui.theme.TIME_LABEL
import com.example.currencyrate.ui.theme.modifiers.currencyDropdownModifier
import com.example.currencyrate.ui.theme.styles.SelectedTextStyle
import com.example.currencyrate.ui.viewmodels.CurrencyViewModel

//Выпадающий список врменных интервалов
@Composable
fun TimeDropdown(currencyViewModel: CurrencyViewModel = viewModel()) {
    var expanded by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf(TIME_LABEL) }
    val timeOptions = listOf("Год", "Полгода", "Три месяца", "Месяц", "Неделя")

    Card(modifier = Modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Text(
            text = selectedTime,
            modifier = Modifier.currencyDropdownModifier { expanded = true },
            style = SelectedTextStyle
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            timeOptions.forEach { time ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = time,
                            modifier = Modifier
                                .currencyDropdownModifier {
                                    selectedTime = time
                                    expanded = false
                                    currencyViewModel.changeTimeInterval(time)
                                },
                            style = SelectedTextStyle
                        )
                    },
                    onClick = { }
                )
            }
        }
    }
}