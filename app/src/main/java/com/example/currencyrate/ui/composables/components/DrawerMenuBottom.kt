package com.example.currencyrate.ui.composables.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.currencyrate.ui.theme.MENU_DESCRIPTION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}