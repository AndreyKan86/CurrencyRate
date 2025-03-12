package com.example.currencyrate.ui.composables.scaffold

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyrate.ui.composables.components.DrawerMenuBottom
import com.example.currencyrate.ui.composables.components.TitleTop
import com.example.currencyrate.ui.composables.scaffold.mainscreen.MainScreen
import com.example.currencyrate.ui.composables.scaffold.menudrawer.DrawerContent
import com.example.currencyrate.ui.theme.CurrencyRateTheme
import com.example.currencyrate.ui.theme.GoldU
import com.example.currencyrate.ui.viewmodels.CurrencyViewModel
import kotlinx.coroutines.launch

//Основное поле
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(currencyViewModel: CurrencyViewModel = viewModel()) {
    val isDarkTheme by currencyViewModel.isDarkTheme.collectAsState()

    CurrencyRateTheme(darkTheme = isDarkTheme) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
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
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    titleContentColor = GoldU
                                )
                            )
                        },
                        bottomBar = {
                            BottomAppBar(
                                containerColor = MaterialTheme.colorScheme.primary
                            ) {
                                BottomMenu()
                            }
                        }
                    ) { innerPadding ->
                        CompositionLocalProvider(LocalContentColor provides Color.Black) {
                            MainScreen(
                                currencyViewModel = currencyViewModel,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
    }