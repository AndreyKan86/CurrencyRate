package com.example.currencyrate.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Цвета для светлой темы
val LightColorScheme = lightColorScheme(
    primary = Color.DarkGray,
    onPrimary = Color(0xFFDAAD86),
    secondary = Color.LightGray,
    background = Color.White
)

// Цвета для тёмной темы
val DarkColorScheme = darkColorScheme(
    primary = Color.Black,
    onPrimary = Color(0xFFDAAD86),
    secondary = Color.DarkGray,
    background = Color.LightGray
)

@Composable
fun CurrencyRateTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}