package com.example.currencyrate.ui.theme.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Модификатор для фона главного экрана
fun Modifier.mainScreenBackground(): Modifier = this
    .fillMaxSize()
    .background(Color.LightGray)

// Модификатор для разделителей
fun Modifier.customDivider(color: Color = Color.DarkGray): Modifier = this
    .fillMaxWidth()
    .background(color)

// Модификатор для текста в верхнем правом углу
fun Modifier.topRightText(): Modifier = this
    .fillMaxWidth()
    .padding(8.dp)