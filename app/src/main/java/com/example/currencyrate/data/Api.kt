package com.example.currencyrate.data

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import retrofit2.http.GET

interface CurrencyApi {
    @GET
    suspend fun getCurrencyRates(url: String): List<CurrencyRate> {
        val currencyRates = mutableListOf<CurrencyRate>()
        val doc: Document = Jsoup.connect(url).get()

        val tableRows: Elements = doc.select(".gfcont table tr")

        for (i in 1 until tableRows.size) {
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
}
