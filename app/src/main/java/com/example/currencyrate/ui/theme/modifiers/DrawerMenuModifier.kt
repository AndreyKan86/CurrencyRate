package com.example.currencyrate.ui.theme.modifiers

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.dp

//Выпадающее меню
fun Modifier.currencyDropdownModifier(onClick: () -> Unit): Modifier =
    this
        .clickable { onClick() }
        .padding(10.dp)
        .padding(horizontal = 8.dp)

//Копка приступить
fun Modifier.buttomStart(): Modifier =
    this
        .padding(bottom = 2.dp)

//Главная надпись
fun Modifier.titleBoxModifier() = this
    .padding(horizontal = 8.dp)
    .padding(end = 26.dp)
    .fillMaxWidth()

//Изображение логотипа
fun Modifier.logoImageModifier(uriHandler: UriHandler) = this
    .size(80.dp)
    .border(2.dp, Color.DarkGray, CircleShape)
    .clip(CircleShape)
    .clickable { uriHandler.openUri("https://t.me/tumultuari") }

