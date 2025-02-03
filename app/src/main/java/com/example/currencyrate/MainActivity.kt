package com.example.currencyrate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyrate.data.CurrencyRepository
import com.example.currencyrate.ui.CurrencyViewModel
import com.example.currencyrate.ui.CurrencyViewModelFactory
import com.example.currencyrate.ui.MainScaffold

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val repository = CurrencyRepository()
            val viewModel: CurrencyViewModel = viewModel(factory = CurrencyViewModelFactory(repository))

            MainScaffold(viewModel = viewModel)
        }
    }
}

