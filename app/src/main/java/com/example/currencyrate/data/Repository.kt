package com.example.currencyrate.data

import retrofit2.Response

class CurrencyRepository {
    suspend fun getCurrencyRates(url: String): Response<String> {
        return RetrofitInstance.api.getCurrencyRates(url)
    }
}