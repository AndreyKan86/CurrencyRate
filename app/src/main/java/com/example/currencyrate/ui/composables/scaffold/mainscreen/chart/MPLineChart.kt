package com.example.currencyrate.ui.composables.scaffold.mainscreen.chart

import android.icu.util.Calendar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.currencyrate.ui.theme.CURRENCY_RATE_CHART_LABEL
import com.example.currencyrate.ui.theme.KALMAN_FILTER_CHART_LABEL
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.text.SimpleDateFormat

//График
@Composable
fun MPLineChart(data: List<Pair<Float, Float>>, filteredData: List<Pair<Float, Float>>) {
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = false
                setTouchEnabled(true)
                isDragEnabled = true
                setScaleEnabled(true)
                setPinchZoom(true)
                setBackgroundColor(android.graphics.Color.WHITE)

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    textSize = 10f
                    textColor = android.graphics.Color.BLACK
                    setDrawGridLines(true)
                    granularity = 1f
                    labelRotationAngle = -45f
                    axisLineColor = android.graphics.Color.BLACK
                    // Форматирование дат
                    valueFormatter = object : ValueFormatter() {
                        private val dateFormat = SimpleDateFormat("dd.MM")
                        override fun getFormattedValue(value: Float): String {
                            val index = value.toInt()
                            val calendar = Calendar.getInstance()
                            calendar.time = java.util.Date()
                            calendar.add(Calendar.DAY_OF_MONTH, -(data.size - 1 - index))
                            return dateFormat.format(calendar.time)
                        }
                    }
                    setLabelCount(data.size, true)
                }

                axisLeft.apply {
                    textSize = 10f
                    textColor = android.graphics.Color.BLACK
                    setDrawGridLines(true)
                    axisLineColor = android.graphics.Color.BLACK
                }

                axisRight.isEnabled = false

                description.text = "Currency Rate Chart"
                description.textColor = android.graphics.Color.BLACK
                description.textSize = 12f
            }
        },
        update = { chart ->
            val entries = data.map { Entry(it.first, it.second) }
            val dataSet = LineDataSet(entries, CURRENCY_RATE_CHART_LABEL)
            dataSet.color = android.graphics.Color.RED
            dataSet.setCircleColor(android.graphics.Color.RED)
            dataSet.lineWidth = 1f
            dataSet.circleRadius = 2f
            dataSet.setDrawCircleHole(false)
            dataSet.valueTextSize = 8f
            dataSet.setDrawFilled(false)
            dataSet.fillColor = android.graphics.Color.argb(100, 255, 0, 0)

            val filteredEntries = filteredData.map { Entry(it.first, it.second) }
            val filteredDataSet = LineDataSet(filteredEntries, KALMAN_FILTER_CHART_LABEL)
            filteredDataSet.color = android.graphics.Color.BLUE
            filteredDataSet.setCircleColor(android.graphics.Color.BLUE)
            filteredDataSet.lineWidth = 2f
            filteredDataSet.circleRadius = 3f
            filteredDataSet.setDrawCircleHole(false)
            filteredDataSet.valueTextSize = 8f
            filteredDataSet.setDrawFilled(false)
            filteredDataSet.fillColor = android.graphics.Color.argb(100, 0, 0, 255)

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(dataSet)
            dataSets.add(filteredDataSet)

            val lineData = LineData(dataSets)

            chart.data = lineData

            chart.invalidate()
        }
    )
}