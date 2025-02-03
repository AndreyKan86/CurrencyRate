package com.example.currencyrate.data

import retrofit2.Retrofit

object RetrofitInstance {
    private const val BASE_URL = "https://www.cbr.ru/"

    val api: CurrencyApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()
            .create(CurrencyApi::class.java)
    }
}