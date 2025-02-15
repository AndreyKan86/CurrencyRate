package com.example.currencyrate.ui.composables.scaffold.mainscreen.chart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.currencyrate.ui.theme.CURRENCY_RATE_CHART_LABEL
import com.example.currencyrate.ui.theme.KALMAN_FILTER_CHART_LABEL
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

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