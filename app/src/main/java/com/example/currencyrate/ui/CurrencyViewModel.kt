package com.example.currencyrate.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyrate.data.CurrencyRate
import com.example.currencyrate.data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CurrencyViewModel : ViewModel() {
    private val _currencyRates = MutableStateFlow<List<CurrencyRate>>(emptyList())
    val currencyRates: StateFlow<List<CurrencyRate>> = _currencyRates

    private val _error = MutableStateFlow<String?>(null)

    val today = LocalDate.now()
    val dayFormatter = DateTimeFormatter.ofPattern("dd")
    val monthFormatter = DateTimeFormatter.ofPattern("M")
    val yearFormatter = DateTimeFormatter.ofPattern("yyyy")



    private val baseUrl = "https://www.val.ru/valhistory.asp?tool="
    private var currencyTool = "978"
    private var startDateUrl = ""
    private var endDateUrl = ""
    private val otherUrl = "&showchartp=False"

    init {
        updateEndDateUrl()
        changeTimeInterval("Неделя")
    }


    fun loadCurrencyRates() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fullUrl = baseUrl + currencyTool + startDateUrl + endDateUrl + otherUrl
                val rates = RetrofitInstance.api.getCurrencyRates(fullUrl)
                _currencyRates.value = rates
            } catch (e: Exception) {
                e.printStackTrace()
                _currencyRates.value = emptyList()
                _error.value = "Ошибка при загрузке данных: ${e.message}"
            }
        }
    }

    fun changeCurrency(currency: String) {
        currencyTool = when (currency) {
            "EUR" -> "978"
            "USD" -> "840"
            "JPY" -> "392"
            "GBP" -> "826"
            else -> "978"
        }
    }

    fun changeTimeInterval(time: String) {
        Log.d("CurrencyViewModel", "changeTimeInterval called with: $time")
        val today = LocalDate.now()
        val startDate: LocalDate = when (time) {
            "Неделя" -> today.minusWeeks(1)
            "Месяц" -> today.minusMonths(1)
            "Три месяца" -> today.minusMonths(3)
            "Пол года" -> today.minusMonths(6)
            "Год" -> today.minusYears(1)
            else -> today.minusWeeks(1)
        }
        startDateUrl = "&bd=${startDate.format(dayFormatter)}&bm=${startDate.format(monthFormatter)}&by=${startDate.format(yearFormatter)}"
    }

    private fun updateEndDateUrl() {
        val today = LocalDate.now()
        endDateUrl = "&ed=${today.format(dayFormatter)}&em=${today.format(monthFormatter)}&ey=${today.format(yearFormatter)}"
    }
}