package com.example.currencyrate.ui.theme.styles

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyrate.ui.theme.GoldU
import com.example.currencyrate.ui.theme.Montserrat
import com.example.currencyrate.ui.theme.Roboto

val TitleTextStyle = TextStyle(
    fontFamily = Montserrat,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp
)

val CompanyNameTextStyle = TextStyle(
    color = Color.DarkGray,
    fontSize = 24.sp,
    textDecoration = TextDecoration.Underline,
    fontFamily = Roboto,
)

val DateTextStyle = TextStyle(
    fontFamily = Roboto,
)

val SelectedTextStyle = TextStyle(
    fontFamily = Montserrat,
    color = GoldU
)

val StartButtomStyle = TextStyle(
    fontFamily = Montserrat,
    color = GoldU
)

val TitleMainScreen = TextStyle (
    fontSize = 20.sp,
    textAlign = TextAlign.Center,
    fontFamily = Montserrat
)
