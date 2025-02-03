package com.example.currencyrate.ui

import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.currencyrate.data.CurrencyRate
import com.example.currencyrate.data.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {

    private val _currencyRates = MutableStateFlow<List<CurrencyRate>>(emptyList())
    val currencyRates: StateFlow<List<CurrencyRate>> = _currencyRates

    fun getCurrencyRates(url: String) {
        viewModelScope.launch {
            val response = repository.getCurrencyRates(url)
            if (response.isSuccessful) {
                val htmlString = response.body()
                if (htmlString != null) {
                    val doc = Jsoup.parse(htmlString)
                    val elements = doc.select("table.data tr")
                    val rates = mutableListOf<CurrencyRate>()
                    for (element in elements) {
                        val tds = element.select("td")
                        if (tds.size == 3) {
                            val date = tds[0].text()
                            val value = tds[2].text()
                            rates.add(CurrencyRate(date, value))
                        }
                    }
                    _currencyRates.value = rates
                }
            } else {
                //TODO: Обработка ошибки
            }
        }
    }
}

class CurrencyViewModelFactory(private val repository: CurrencyRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrencyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}