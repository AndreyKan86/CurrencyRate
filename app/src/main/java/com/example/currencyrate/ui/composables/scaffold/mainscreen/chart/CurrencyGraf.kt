package com.example.currencyrate.ui.composables.scaffold.mainscreen.chart

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyrate.ui.data.CurrencyRate
import com.example.currencyrate.ui.viewmodels.CurrencyViewModel

//Построение графика
@Composable
fun CurrencyGraf(currencyViewModel: CurrencyViewModel = viewModel()) {
    val currencyRates: List<CurrencyRate> by currencyViewModel.currencyRates.collectAsState()
    val filteredCurrencyRates: List<Double> by currencyViewModel.filteredCurrencyRates.collectAsState()
    val error by currencyViewModel.error.collectAsState()

    if (currencyRates.isNotEmpty() && filteredCurrencyRates.isNotEmpty()) {
        val reversedCurrencyRates = currencyRates.reversed()

        val chartData = reversedCurrencyRates.mapIndexed { index, rate ->
            Pair(index.toFloat(), rate.value.toFloat())
        }
        val reversedFilteredCurrencyRates = filteredCurrencyRates.reversed()
        val filteredChartData = reversedFilteredCurrencyRates.mapIndexed { index, rate ->
            Pair(index.toFloat(), rate.toFloat())
        }
        MPLineChart(data = chartData, filteredData = filteredChartData)
    } else {
        if (error != null) {
            Text(text = "Ошибка: $error")
        }
    }
}