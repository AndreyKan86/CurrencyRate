package com.example.currencyrate.ui.composables.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.currencyrate.ui.theme.APP_TITLE
import com.example.currencyrate.ui.theme.modifiers.titleBoxModifier
import com.example.currencyrate.ui.theme.styles.TitleTextStyle

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
