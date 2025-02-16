package com.example.currencyrate.ui.theme.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.dp

// Модификатор для фона главного экрана
@Composable
fun Modifier.mainScreenBackground(): Modifier = this
    .fillMaxSize()
    .background(MaterialTheme.colorScheme.background)

// Модификатор для текста в верхнем правом углу
fun Modifier.topRightText(): Modifier = this
    .fillMaxWidth()
    .padding(8.dp)

fun Modifier.currencyDropdownModifier(onClick: () -> Unit): Modifier =
    this
        .clickable { onClick() }
        .padding(10.dp)
        .padding(horizontal = 8.dp)

//Главная надпись
fun Modifier.titleBoxModifier() = this
    .padding(horizontal = 8.dp)
    .padding(end = 26.dp)
    .fillMaxWidth()

//Изображение логотипа
@Composable
fun Modifier.logoImageModifier(uriHandler: UriHandler) = this
    .size(80.dp)
    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
    .clip(CircleShape)
    .clickable { uriHandler.openUri("https://t.me/tumultuari") }