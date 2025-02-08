package com.example.currencyrate.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyrate.R
import com.example.currencyrate.data.CurrencyRate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.currencyrate.ui.theme.*

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
                            containerColor = BlueU,
                            titleContentColor = GoldU
                        )
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        containerColor = BlueU,
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
    Box(modifier = Modifier
        .padding(horizontal = 8.dp)
        .padding(end = 26.dp)
        .fillMaxWidth(),
        contentAlignment = Alignment.Center){
        Text("Курс валют",
            style = TextStyle(fontFamily = Montserrat, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        )
    }
}

//Кнопка меню
@Composable
fun DrawerMenuBottom(drawerState: DrawerState,
                     scope: CoroutineScope)
{
    IconButton(
        onClick = {
            scope.launch {
                drawerState.open()
            }
        }
    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu",
            tint = Color.LightGray
        )
    }
}

//Содержимое выплывающего меню
@Composable
fun DrawerContent(onItemClick: () -> Unit) {
    ModalDrawerSheet()
    {
        ConstraintLayout()
        {
            val (box1, box2, upDivider, bottomDivider) = createRefs()
            Box(
                modifier = Modifier.constrainAs(box1) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(80.dp)
                        .border(2.dp, Color.DarkGray, CircleShape)
                        .clip(CircleShape)
                )
            }
            Box(
                modifier = Modifier
                    .constrainAs(box2) {
                        top.linkTo(parent.top, margin = 40.dp)
                        start.linkTo(box1.end, margin = 16.dp)
                    }
            )
            {
                Text(
                    text = "\"СиФ\"",
                    color = Color.DarkGray,
                    fontSize = 24.sp,
                    textDecoration = TextDecoration.Underline,
                    fontFamily = Roboto
                )
            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(upDivider) {
                        top.linkTo(parent.top, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                color = Color.DarkGray,
                thickness = 2.dp
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(bottomDivider) {
                        top.linkTo(box1.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                color = Color.DarkGray,
                thickness = 2.dp
            )
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
        Text(text = "Date: ${rate.date}", style = MaterialTheme.typography.bodyMedium, fontFamily = Roboto)
        Text(text = "Rate: ${rate.value}", style = MaterialTheme.typography.bodyMedium, fontFamily = Roboto)
    }
}

//Выпадающий список валют
@Composable
fun CurrencyDropdown(currencyViewModel: CurrencyViewModel = viewModel()) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCurrency by remember { mutableStateOf("Валюта") }
    val currencyOptions = listOf("EUR", "USD", "JPY", "GBP")

    Card(modifier = Modifier,
        colors = CardDefaults.cardColors(containerColor = VioletU)
    )
    {
        Text(
            text = selectedCurrency,
            modifier = Modifier
                .clickable { expanded = true }
                .padding(6.dp)
                .padding(horizontal = 8.dp),
            fontFamily = Montserrat,
            color = GoldU
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencyOptions.forEach { currency ->
                DropdownMenuItem(
                    text = { Text(text = currency,
                        modifier = Modifier
                            .padding(4.dp),
                        fontFamily = Montserrat,
                        color = GoldU
                    ) },
                    onClick = {
                        selectedCurrency = currency
                        expanded = false
                        currencyViewModel.changeCurrency(currency)
                    }
                )
            }
        }
    }
}

//Выпадающий список врменных интервалов
@Composable
fun TimeDropdown(currencyViewModel: CurrencyViewModel = viewModel()) {
    var expanded by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("Период") }
    val timeOptions = listOf("Год", "Полгода", "Три месяца", "Месяц", "Неделя")

    Card(modifier = Modifier,
        colors = CardDefaults.cardColors(containerColor = VioletU)
    ) {
        Text(
            text = selectedTime,
            modifier = Modifier
                .clickable { expanded = true }
                .padding(6.dp)
                .padding(horizontal = 8.dp),
            fontFamily = Montserrat,
            color = GoldU
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            timeOptions.forEach { time ->
                DropdownMenuItem(
                    text = { Text(text = time,
                        modifier = Modifier
                            .padding(4.dp),
                        fontFamily = Montserrat,
                        color = GoldU
                    ) },
                    onClick = {
                        selectedTime = time
                        expanded = false

                        currencyViewModel.changeTimeInterval(time)
                    }
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
            TimeDropdown()
        }

        Box(modifier = Modifier.constrainAs(box3){
            top.linkTo(parent.top, margin = 8.dp)
            end.linkTo(parent.end, margin = 24.dp)
        })
        {
            Button(onClick = {
                currencyViewModel.loadCurrencyRates()
            },
                colors = ButtonDefaults.buttonColors(containerColor = VioletU) ) {
                Text( "Приступить",
                    modifier = Modifier.padding(bottom = 2.dp),
                    fontFamily = Montserrat,
                    color = GoldU
                    )
            }
        }
    }
}

//Главный экран
@Composable
fun MainScreen(currencyViewModel: CurrencyViewModel = viewModel(), modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier
        .fillMaxSize()
        .background(GreyU)) {
        val (boxTopLeft, boxTopRight, boxBottomLeft, boxBottomRight, horizontalDivider,
            vericalDivider, rightTopText) = createRefs()
        val horizontalGuideline = createGuidelineFromTop(0.5f)
        val verticalGuideline = createGuidelineFromStart(0.5f)

        val selectedCurrency by currencyViewModel.selectedCurrency.collectAsState()
        val selectedTimeInterval by currencyViewModel.selectedTimeInterval.collectAsState()

        Box(modifier = Modifier
            .constrainAs(rightTopText) {
                top.linkTo(parent.top)
                start.linkTo(verticalGuideline)
                end.linkTo(parent.end)
            }
            .fillMaxWidth(),
            contentAlignment = Alignment.Center
        )
        {
            Text(
                text = "Валюта: $selectedCurrency \nПериод: $selectedTimeInterval",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontFamily = Montserrat
            )
        }

        Box(modifier = Modifier.constrainAs(boxTopRight){
            top.linkTo(rightTopText.bottom)
            bottom.linkTo(horizontalGuideline)
            start.linkTo(verticalGuideline)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
        )
        {
            CurrencyRateList()
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(horizontalDivider) {
                top.linkTo(horizontalGuideline)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .background(color = Color.Black))
        {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Box(Modifier
            .fillMaxHeight()
            .constrainAs(vericalDivider) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(verticalGuideline)
            }
        ){
            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight()
            )
        }
        Box(Modifier
            .fillMaxSize()
            .constrainAs(boxBottomLeft) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(verticalGuideline)
                width = Dimension.fillToConstraints
            })
        {
            //Место для кода
        }

        Box(Modifier
            .fillMaxSize()
            .constrainAs(boxBottomRight) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(verticalGuideline)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            })
        {
            //Место для функции
        }

        Box(Modifier
            .fillMaxSize()
            .constrainAs(boxTopLeft) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(verticalGuideline)
                width = Dimension.fillToConstraints
            })
        {
            //Место для функции
        }
    }
}

