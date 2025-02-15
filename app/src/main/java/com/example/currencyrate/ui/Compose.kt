package com.example.currencyrate.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyrate.R
import com.example.currencyrate.data.CurrencyRate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.currencyrate.ui.theme.*
import com.example.currencyrate.ui.theme.modifiers.bottomDividerModifier
import com.example.currencyrate.ui.theme.modifiers.buttomStart
import com.example.currencyrate.ui.theme.modifiers.companyNameBoxModifier
import com.example.currencyrate.ui.theme.modifiers.currencyDropdownModifier
import com.example.currencyrate.ui.theme.modifiers.customDivider
import com.example.currencyrate.ui.theme.modifiers.logoBoxModifier
import com.example.currencyrate.ui.theme.modifiers.logoImageModifier
import com.example.currencyrate.ui.theme.modifiers.mainScreenBackground
import com.example.currencyrate.ui.theme.modifiers.titleBoxModifier
import com.example.currencyrate.ui.theme.modifiers.topDividerModifier
import com.example.currencyrate.ui.theme.modifiers.topRightText
import com.example.currencyrate.ui.theme.styles.CompanyNameTextStyle
import com.example.currencyrate.ui.theme.styles.DateTextStyle
import com.example.currencyrate.ui.theme.styles.SelectedTextStyle
import com.example.currencyrate.ui.theme.styles.StartButtomStyle
import com.example.currencyrate.ui.theme.styles.TitleMainScreen
import com.example.currencyrate.ui.theme.styles.TitleTextStyle
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.time.LocalDate
import kotlin.text.toFloat

//Основное поле
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(currencyViewModel: CurrencyViewModel = viewModel()) {
    MaterialTheme(
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet{
                    DrawerContent { scope.launch { drawerState.close() } }
                }
            }
        )
        {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            TitleTop()
                        },
                        navigationIcon = {
                            DrawerMenuBottom(drawerState = drawerState, scope = scope)
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Black,
                            titleContentColor = GoldU
                        )
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ) {

                        BottomButton()

                    }
                }
            ) { innerPadding ->
                MainScreen(currencyViewModel = currencyViewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

//Титульная надпись
@Composable
fun TitleTop() {
    Box(
        modifier = Modifier.titleBoxModifier(),
        contentAlignment = Alignment.Center
    )
    {
        Text(text = APP_TITLE,
            style = TitleTextStyle
        )
    }
}

//Кнопка меню
@Composable
fun DrawerMenuBottom(
    drawerState: DrawerState,
    scope: CoroutineScope
)
{
    IconButton(
        onClick = {
            scope.launch {
                drawerState.open()
            }
        }
    )
    {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = MENU_DESCRIPTION,
            tint = Color.LightGray
        )
    }
}

//Содержимое выплывающего меню
@Composable
fun DrawerContent(onItemClick: () -> Unit)
{
    val uriHandler = LocalUriHandler.current

    ModalDrawerSheet()
    {
        ConstraintLayout()
        {
            val (logo, nameCompany, upDivider, bottomDivider, textDescription) = createRefs()
            Box(
                modifier = logoBoxModifier(logo)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = LOGO_DESCRIPTION,
                    modifier = Modifier.logoImageModifier(uriHandler)
                )
            }
            Box(
                modifier = companyNameBoxModifier(nameCompany, logo)
            )
            {
                Text(
                    text = COMPANY_NAME,
                    style = CompanyNameTextStyle
                )
            }
            HorizontalDivider(
                modifier = topDividerModifier(upDivider),
                color = Color.DarkGray,
                thickness = 2.dp
            )
            HorizontalDivider(
                modifier = bottomDividerModifier(bottomDivider, logo),
                color = Color.DarkGray,
                thickness = 2.dp
            )
            Box(modifier = Modifier.constrainAs(textDescription)
            {
                top.linkTo(bottomDivider.top, margin = 12.dp)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
                .padding(6.dp)
            )
            {
                Text(text = DESCRIPTION_APP)
            }
        }
    }
}

//Данные
@Composable
fun CurrencyRateList(currencyViewModel: CurrencyViewModel = viewModel()) {
    val currencyRates: List<CurrencyRate> by currencyViewModel.currencyRates.collectAsState()
    if (currencyRates.isNotEmpty()){
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(currencyRates) { rate ->
                CurrencyRateItem(rate = rate)
            }
        }
    }
}

//Элементы списка
@Composable
fun CurrencyRateItem(rate: CurrencyRate) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        HorizontalDivider(
            modifier = Modifier
            .fillMaxWidth()
        )
        Text(text = DATE_LABEL + rate.date, style = DateTextStyle)
        Text(text = RATE_LABEL + rate.value, style = DateTextStyle)
    }
}

//Выпадающий список валют
@Composable
fun CurrencyDropdown(currencyViewModel: CurrencyViewModel = viewModel()) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCurrency by remember { mutableStateOf(CURRENCY_LABEL) }
    val currencyOptions = listOf("EUR", "USD", "JPY", "GBP")

    Card(modifier = Modifier,
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    )
    {
        Text(
            text = selectedCurrency,
            modifier = Modifier.currencyDropdownModifier { expanded = true },
            style = SelectedTextStyle
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencyOptions.forEach { currency ->
                DropdownMenuItem(
                    text = { Text(text = currency,
                        modifier = Modifier.currencyDropdownModifier {
                            selectedCurrency = currency
                            expanded = false
                            currencyViewModel.changeCurrency(currency) },
                        style = SelectedTextStyle
                    ) },
                    onClick = { }
                )
            }
        }
    }
}

//Выпадающий список врменных интервалов
@Composable
fun TimeDropdown(currencyViewModel: CurrencyViewModel = viewModel()) {
    var expanded by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf(TIME_LABEL) }
    val timeOptions = listOf("Год", "Полгода", "Три месяца", "Месяц", "Неделя")

    Card(modifier = Modifier,
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Text(
            text = selectedTime,
            modifier = Modifier.currencyDropdownModifier { expanded = true },
            style = SelectedTextStyle
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            timeOptions.forEach { time ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = time,
                            modifier = Modifier
                                .currencyDropdownModifier {
                                    selectedTime = time
                                    expanded = false
                                    currencyViewModel.changeTimeInterval(time)
                                },
                            style = SelectedTextStyle
                        )
                    },
                    onClick = { }
                )
            }
        }
    }
}

//Нижнее меню
@Composable
fun BottomButton(currencyViewModel: CurrencyViewModel = viewModel()) {
    ConstraintLayout (modifier = Modifier
        .fillMaxSize()
    ) {
        val (box1, box2, box3) = createRefs()

        Box(modifier = Modifier.constrainAs(box1){
            top.linkTo(parent.top, margin = 12.dp)
            start.linkTo(parent.start, margin = 24.dp)
        }){
            CurrencyDropdown()
        }

        Box(modifier = Modifier.constrainAs(box2){
            top.linkTo(parent.top, margin = 12.dp)
            start.linkTo(parent.start, margin = 160.dp)
        }){
            TimeDropdown(currencyViewModel)
        }

        Box(modifier = Modifier.constrainAs(box3){
            top.linkTo(parent.top, margin = 8.dp)
            end.linkTo(parent.end, margin = 24.dp)
        })
        {
            Button(onClick = {
                currencyViewModel.loadCurrencyRates()
            },
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray) ) {
                Text( PROCEED_BUTTON_TEXT,
                    modifier = Modifier.buttomStart(),
                    style = StartButtomStyle
                    )
            }
        }
    }
}

//Главный экран
@Composable
fun MainScreen(currencyViewModel: CurrencyViewModel = viewModel(), modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier.mainScreenBackground()) {
        val (boxTopLeft, boxTopRight, boxBottom, horizontalDivider, verticalDivider, rightTopText) = createRefs()
        val horizontalGuideline = createGuidelineFromTop(0.5f)
        val verticalGuideline = createGuidelineFromStart(0.5f)

        val selectedCurrency by currencyViewModel.selectedCurrency.collectAsState()
        val selectedTimeInterval by currencyViewModel.selectedTimeInterval.collectAsState()

        // Текст в верхнем правом углу
        Box(
            modifier = Modifier
                .constrainAs(rightTopText) {
                    top.linkTo(parent.top)
                    start.linkTo(verticalGuideline)
                    end.linkTo(parent.end)
                }
                .topRightText(),
            contentAlignment = Alignment.Center
        )
        {
            Text(
                text = CURRENCY_LABEL + selectedCurrency + "\n" + TIME_LABEL + selectedTimeInterval,
                style = TitleMainScreen
            )
        }

        // Box для списка валют
        Box(
            modifier = Modifier.constrainAs(boxTopRight) {
                top.linkTo(rightTopText.bottom)
                bottom.linkTo(horizontalGuideline)
                start.linkTo(verticalGuideline)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            CurrencyRateList()
        }

        // Горизонтальный разделитель
        Box(
            modifier = Modifier
                .constrainAs(horizontalDivider) {
                    top.linkTo(horizontalGuideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .customDivider()
        ) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.DarkGray
            )
        }

        // Вертикальный разделитель
        Box(
            modifier = Modifier.constrainAs(verticalDivider) {
                top.linkTo(parent.top)
                bottom.linkTo(horizontalDivider.top)
                start.linkTo(verticalGuideline)
                height = Dimension.fillToConstraints
            }
        ) {
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = Color.DarkGray
            )
        }

        // Box для легенды
        Box(
            modifier = Modifier.constrainAs(boxTopLeft) {
                top.linkTo(parent.top)
                bottom.linkTo(horizontalDivider.top)
                start.linkTo(parent.start)
                end.linkTo(verticalGuideline)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            Legend()
        }

        // Box для графика
        Box(
            modifier = Modifier.constrainAs(boxBottom) {
                top.linkTo(horizontalDivider.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            CurrencyGraf()
        }
    }
}

//Легенда
@Composable
fun Legend (currencyViewModel: CurrencyViewModel = viewModel()){
    val today = LocalDate.now()
    val predictedValue by currencyViewModel.predictedValue.collectAsState()
    val selectedCurrency by currencyViewModel.selectedCurrency.collectAsState()

    val currencyImageMap = mapOf(
        "USD" to R.drawable.usd,
        "EUR" to R.drawable.eur,
        "JPY" to R.drawable.jpy,
        "GBP" to R.drawable.gbp,
    )

    val selectedCurrencyImage = currencyImageMap[selectedCurrency]
    val imageToDisplay = selectedCurrencyImage ?: R.drawable.logo


    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (textRef, columnRef, imageRef) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(textRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(text = LEGEND_TEXT,
                style = TitleMainScreen
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(imageRef) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                    painter = painterResource(id = imageToDisplay),
                    contentDescription = selectedCurrency + LOGO_DESCRIPTION,
                    modifier = Modifier
                        .size(200.dp)
            )
        }
        Box (
            modifier = Modifier.constrainAs(columnRef){
                start.linkTo(parent.start)
                top.linkTo(textRef.bottom)
                end.linkTo(parent.end)
            }
        ) {
            Column {
                predictedValue?.let {
                    val roundedPredictedValue = String.format("%.2f", it)
                    Text(text = "Прогноз: ${today.plusDays(1)}: $roundedPredictedValue")
                } ?: Text(text = "Нет прогноза")
            }
        }
    }
}

//Построение графика
@Composable
fun CurrencyGraf(currencyViewModel: CurrencyViewModel = viewModel()) {
    val currencyRates: List<CurrencyRate> by currencyViewModel.currencyRates.collectAsState()
    val filteredCurrencyRates: List<Double> by currencyViewModel.filteredCurrencyRates.collectAsState()
    val error by currencyViewModel.error.collectAsState()

    if (currencyRates.isNotEmpty() && filteredCurrencyRates.isNotEmpty()) {
        val reversedCurrencyRates = currencyRates.reversed()

        val chartData = reversedCurrencyRates.mapIndexed { index, rate ->
            Pair(index.toFloat(), rate.value.toFloat())
        }
        val reversedFilteredCurrencyRates = filteredCurrencyRates.reversed()
        val filteredChartData = reversedFilteredCurrencyRates.mapIndexed { index, rate ->
            Pair(index.toFloat(), rate.toFloat())
        }
        MPLineChart(data = chartData, filteredData = filteredChartData)
    } else {
        if (error != null) {
            Text(text = "Ошибка: $error")
        }
    }
}

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

