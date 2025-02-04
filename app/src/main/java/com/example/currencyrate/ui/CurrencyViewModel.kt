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

class CurrencyViewModel : ViewModel() {
    private val _currencyRates = MutableStateFlow<List<CurrencyRate>>(emptyList())
    val currencyRates: StateFlow<List<CurrencyRate>> = _currencyRates

    private val url = "https://www.val.ru/valhistory.asp?tool=978&bd=4&bm=12&by=2024&ed=4&em=2&ey=2025&showchartp=False"

    init {
        loadCurrencyRates()
    }

    private fun loadCurrencyRates() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val rates = RetrofitInstance.api.getCurrencyRates(url)
                _currencyRates.value = rates
                Log.i("CurrencyViewModel", "ВЬЮМОДЕЛЬ: ${rates.size} items")
            } catch (e: Exception) {
                e.printStackTrace()
                _currencyRates.value = emptyList()
                Log.e("CurrencyViewModel", "ОШИБКА", e)
            }
        }
    }
}