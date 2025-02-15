package com.example.currencyrate.ui.composables.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ThemeSwitcher(
    isDarkTheme: Boolean,
    onThemeChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.padding(16.dp)) {
        Text(
            text = if (isDarkTheme) "Dark" else "Light",
            modifier = Modifier.padding(end = 8.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
        Switch(
            checked = isDarkTheme,
            onCheckedChange = onThemeChanged,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.LightGray,
            )
        )
    }
}