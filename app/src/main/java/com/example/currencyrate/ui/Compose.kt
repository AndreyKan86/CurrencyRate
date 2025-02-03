package com.example.currencyrate.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
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
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.currencyrate.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.currencyrate.ui.theme.*

//Основное поле
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(viewModel: CurrencyViewModel) {
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
                            containerColor = BlackU,
                            titleContentColor = OrangeU
                        )
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    ) {
                        Text("Bottom Bar")
                    }
                }
            ) { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    Text("Курс евро")
                }            }
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
        Text("Курс валют")
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
                    fontFamily = FontFamily.Cursive
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

//Информация по курсу валют
@Composable
fun CurrencyScreen(viewModel: CurrencyViewModel) {
    val currencyRates by viewModel.currencyRates.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        LazyColumn {
            items(currencyRates) { rate ->
                Text(text = "${rate.date}: ${rate.value}")
            }
        }
    }
    viewModel.getCurrencyRates("https://www.cbr.ru/currency_base/daily/")
}