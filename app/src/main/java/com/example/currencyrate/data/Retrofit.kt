package com.example.currencyrate.data

object RetrofitInstance {
    val api: CurrencyApi by lazy {
        object : CurrencyApi {}
    }
}