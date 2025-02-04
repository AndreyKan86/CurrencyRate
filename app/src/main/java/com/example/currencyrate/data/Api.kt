package com.example.currencyrate.data

import android.util.Log
import okio.IOException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

interface CurrencyApi {
    @Throws(IOException::class)
    suspend fun getCurrencyRates(url: String): List<CurrencyRate> {
        val currencyRates = mutableListOf<CurrencyRate>()
        val doc: Document = Jsoup.connect(url).get()

        Log.d("CurrencyApi", "Full HTML: ${doc.html()}")

        val tableRows: Elements = doc.select(".gfcont table tr")

        Log.d("CurrencyApi", "СТРОК НАЙДЕНО: ${tableRows.size}")

        for (i in 1 until tableRows.size) {
            val row = tableRows[i]
            val columns = row.select("td")
            if (columns.size >= 2) {
                val date = columns[0].text()
                val rate = columns[2].text()

                Log.d("CurrencyApi", "ДАТА: $date, Rate: $rate")
                currencyRates.add(CurrencyRate(date, rate))
            }
        }
        return currencyRates
    }
}
