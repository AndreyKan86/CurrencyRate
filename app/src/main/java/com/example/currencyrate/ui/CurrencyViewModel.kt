package com.example.currencyrate.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyrate.data.CurrencyRate
import com.example.currencyrate.data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CurrencyViewModel : ViewModel() {
    private val _currencyRates = MutableStateFlow<List<CurrencyRate>>(emptyList())
    val currencyRates: StateFlow<List<CurrencyRate>> = _currencyRates

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _selectedCurrency = MutableStateFlow("")
    val selectedCurrency: StateFlow<String> = _selectedCurrency.asStateFlow()

    private val _selectedTimeInterval = MutableStateFlow("")
    val selectedTimeInterval: StateFlow<String> = _selectedTimeInterval.asStateFlow()

    val dayFormatter = DateTimeFormatter.ofPattern("dd")
    val monthFormatter = DateTimeFormatter.ofPattern("M")
    val yearFormatter = DateTimeFormatter.ofPattern("yyyy")

    private val baseUrl = "https://www.val.ru/valhistory.asp?tool="
    private var currencyTool = ""
    private var startDateUrl = ""
    private var endDateUrl = ""
    private val otherUrl = "&showchartp=False"

    init {
        updateEndDateUrl()
    }

    fun loadCurrencyRates() {
        if (_selectedCurrency.value.isBlank() || _selectedTimeInterval.value.isBlank()) {
            _error.value = "Выберите валюту и временной интервал"
            _currencyRates.value = emptyList()
            return
        }
        viewModelScope.launch {
            try {
                val fullUrl = baseUrl + currencyTool + startDateUrl + endDateUrl + otherUrl
                val html = RetrofitInstance.api.getCurrencyRates(fullUrl)
                if (html.isEmpty()){
                    _error.value = "Страница пустая"
                    _currencyRates.value = emptyList()
                } else {
                    val rates = parseHtml(html)
                    _currencyRates.value = rates

                }
            } catch (e: IOException) {
                _error.value = "Ошибка сети: проверьте подключение к интернету"
                _currencyRates.value = emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "Неизвестная ошибка: ${e.message}"
                _currencyRates.value = emptyList()
            }
        }
    }

    private fun parseHtml(html: String): List<CurrencyRate> {
        val currencyRates = mutableListOf<CurrencyRate>()
        val doc: Document = Jsoup.parse(html)

        val tableRows: Elements = doc.select(".gfcont table tr")
        if (tableRows.isEmpty()){
            throw Exception("Не удалось получить данные")
        }

        for (i in 0 until tableRows.size) {
            val row = tableRows[i]
            val columns = row.select("td")
            if (columns.size >= 2) {
                val date = columns[0].text()
                val rate = columns[2].text()
                currencyRates.add(CurrencyRate(date, rate))
            }
        }
        return currencyRates
    }

    fun changeCurrency(currency: String) {
        currencyTool = when (currency) {
            "EUR" -> "978"
            "USD" -> "840"
            "JPY" -> "392"
            "GBP" -> "826"
            else -> "978"
        }
        _selectedCurrency.value = currency
    }

    fun changeTimeInterval(time: String) {
        val today = LocalDate.now()
        val startDate: LocalDate = when (time) {
            "Неделя" -> today.minusWeeks(1)
            "Месяц" -> today.minusMonths(1)
            "Три месяца" -> today.minusMonths(3)
            "Полгода" -> today.minusMonths(6)
            "Год" -> today.minusYears(1)
            else -> today.minusWeeks(1)
        }
        startDateUrl = "&bd=${startDate.format(dayFormatter)}&bm=${startDate.format(monthFormatter)}&by=${startDate.format(yearFormatter)}"
        _selectedTimeInterval.value = time
    }

    private fun updateEndDateUrl() {
        val today = LocalDate.now()
        endDateUrl = "&ed=${today.format(dayFormatter)}&em=${today.format(monthFormatter)}&ey=${today.format(yearFormatter)}"
    }

}