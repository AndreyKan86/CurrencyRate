package com.example.currencyrate.ui.data

import retrofit2.http.GET
import retrofit2.http.Url

interface CurrencyApi {
    @GET
    suspend fun getCurrencyRates(@Url url: String): String
}
