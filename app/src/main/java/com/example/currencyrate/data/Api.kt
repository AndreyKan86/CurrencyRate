package com.example.currencyrate.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface CurrencyApi {
    @GET
    suspend fun getCurrencyRates(@Url url: String): Response<String>
}

