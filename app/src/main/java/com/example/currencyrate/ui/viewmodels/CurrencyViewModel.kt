package com.example.currencyrate.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyrate.ui.data.CurrencyRate
import com.example.currencyrate.ui.data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import org.apache.commons.math3.linear.ArrayRealVector
import org.apache.commons.math3.linear.MatrixUtils
import org.apache.commons.math3.linear.RealMatrix
import org.apache.commons.math3.linear.RealVector
import org.apache.commons.math3.linear.SingularMatrixException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.text.toDouble

class CurrencyViewModel : ViewModel() {
    private val _currencyRates = MutableStateFlow<List<CurrencyRate>>(emptyList())
    val currencyRates: StateFlow<List<CurrencyRate>> = _currencyRates

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _selectedCurrency = MutableStateFlow("")
    val selectedCurrency: StateFlow<String> = _selectedCurrency.asStateFlow()

    private val _selectedTimeInterval = MutableStateFlow("")
    val selectedTimeInterval: StateFlow<String> = _selectedTimeInterval.asStateFlow()

    private val _filteredCurrencyRates = MutableStateFlow<List<Double>>(emptyList())
    val filteredCurrencyRates: StateFlow<List<Double>> = _filteredCurrencyRates.asStateFlow()

    private val _predictedValue = MutableStateFlow<Double?>(null)
    val predictedValue: StateFlow<Double?> = _predictedValue.asStateFlow()

    val dayFormatter = DateTimeFormatter.ofPattern("dd")
    val monthFormatter = DateTimeFormatter.ofPattern("M")
    val yearFormatter = DateTimeFormatter.ofPattern("yyyy")

    private val baseUrl = "https://www.val.ru/valhistory.asp?tool="
    private var currencyTool = ""
    private var startDateUrl = ""
    private var endDateUrl = ""
    private val otherUrl = "&showchartp=False"
    private lateinit var ekf: ExtendedKalmanFilter

    private val _isDarkTheme = MutableStateFlow(true)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    init {
        updateEndDateUrl()
        initializeKalmanFilter()
    }

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
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
                    applyKalmanFilter(rates)
                    _predictedValue.value = getPredictedValueForNextDay()
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

    fun getPredictedValueForNextDay(): Double? {
        if (!::ekf.isInitialized) {
            return null
        }
        try {
            ekf.predict()
            return ekf.getState()
        } catch (e: Exception) {
            e.printStackTrace()
            _error.value = "Ошибка при предсказании значения на следующий день: ${e.message}"
            return null
        }
    }

    private fun initializeKalmanFilter() {
        val f: (RealVector) -> RealVector = { x ->
            val dt = 1.0
            val newX = x.copy()
            newX.setEntry(0, x.getEntry(0) + dt * x.getEntry(1))
            newX.setEntry(1, x.getEntry(1))
            newX
        }
        val h: (RealVector) -> RealVector = { x ->
            ArrayRealVector(doubleArrayOf(x.getEntry(0)))
        }
        val jacobianF: (RealVector) -> RealMatrix = { _ ->
            val dt = 1.0
            MatrixUtils.createRealMatrix(arrayOf(
                doubleArrayOf(1.0, dt),
                doubleArrayOf(0.0, 1.0)
            ))
        }
        val jacobianH: (RealVector) -> RealMatrix = { _ ->
            MatrixUtils.createRealMatrix(arrayOf(doubleArrayOf(1.0, 0.0)))
        }
        val Q = MatrixUtils.createRealMatrix(arrayOf(
            doubleArrayOf(0.01, 0.0),
            doubleArrayOf(0.0, 0.001)
        ))
        val R = MatrixUtils.createRealMatrix(arrayOf(doubleArrayOf(0.1)))

        ekf = ExtendedKalmanFilter(f, h, jacobianF, jacobianH, Q, R)
    }

    private fun applyKalmanFilter(rates: List<CurrencyRate>) {
        val doubleRates = rates.mapNotNull {
            try {
                it.value.toDouble()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                _error.value = "Ошибка преобразования курса валюты в число: ${it.value}"
                _filteredCurrencyRates.value = emptyList()
                return
            }
        }
        val reversedDoubleRates = doubleRates.reversed()

        val filteredRates = mutableListOf<Double>()
        try {
            if (reversedDoubleRates.size >= 2) {
                val initialVelocity = reversedDoubleRates[1] - reversedDoubleRates[0]
                ekf.setState(reversedDoubleRates[0], initialVelocity)

                for (rate in reversedDoubleRates) {
                    ekf.predict()
                    ekf.update(ArrayRealVector(doubleArrayOf(rate)))
                    filteredRates.add(ekf.getState())
                }
                _filteredCurrencyRates.value = filteredRates.reversed()
            } else {
                _error.value = "Недостаточно данных для применения фильтра Калмана"
                _filteredCurrencyRates.value = emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _error.value = "Ошибка при применении фильтра Калмана: ${e.message}"
            _filteredCurrencyRates.value = emptyList()
        }
    }

    class ExtendedKalmanFilter(
        private val f: (RealVector) -> RealVector,
        private val h: (RealVector) -> RealVector,
        private val jacobianF: (RealVector) -> RealMatrix,
        private val jacobianH: (RealVector) -> RealMatrix,
        private var Q: RealMatrix,
        private var R: RealMatrix
    ) {
        private var x: RealVector
        private var P: RealMatrix

        init {
            x = ArrayRealVector(doubleArrayOf(0.0, 0.0))
            P = MatrixUtils.createRealIdentityMatrix(2)
        }

        fun predict() {
            val x_pred = f(x)
            val F = jacobianF(x)
            val P_pred = F.multiply(P).multiply(F.transpose()).add(Q)

            x = x_pred
            P = P_pred
        }

        fun update(z: RealVector) {
            val y = z.subtract(h(x))
            val H = jacobianH(x)
            val S = H.multiply(P).multiply(H.transpose()).add(R)
            val K: RealMatrix = try {
                P.multiply(H.transpose()).multiply(MatrixUtils.inverse(S))
            } catch (e: SingularMatrixException) {
                e.printStackTrace()
                MatrixUtils.createRealMatrix(P.getRowDimension(), S.getColumnDimension())
            }

            x = x.add(K.operate(y))
            val I = MatrixUtils.createRealIdentityMatrix(x.getDimension())
            P = I.subtract(K.multiply(H)).multiply(P)
        }

        fun getState(): Double {
            return x.getEntry(0)
        }

        fun setState(value: Double, velocity: Double) {
            x.setEntry(0, value)
            x.setEntry(1, velocity)
        }
    }
}
