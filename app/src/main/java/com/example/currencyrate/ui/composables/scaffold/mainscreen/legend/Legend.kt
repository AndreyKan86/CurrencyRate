package com.example.currencyrate.ui.composables.scaffold.mainscreen.legend

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyrate.R
import com.example.currencyrate.ui.theme.LEGEND_TEXT
import com.example.currencyrate.ui.theme.LOGO_DESCRIPTION
import com.example.currencyrate.ui.theme.styles.TitleMainScreen
import com.example.currencyrate.ui.viewmodels.CurrencyViewModel
import java.time.LocalDate

//Легенда
@Composable
fun Legend (currencyViewModel: CurrencyViewModel = viewModel()){
    val today = LocalDate.now()
    val predictedValue by currencyViewModel.predictedValue.collectAsState()
    val selectedCurrency by currencyViewModel.selectedCurrency.collectAsState()

    val currencyImageMap = mapOf(
        "USD" to R.drawable.usd,
        "EUR" to R.drawable.eur,
        "JPY" to R.drawable.jpy,
        "GBP" to R.drawable.gbp,
    )

    val selectedCurrencyImage = currencyImageMap[selectedCurrency]
    //val imageToDisplay = selectedCurrencyImage  ?: R.drawable.logo


    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (textRef, columnRef, imageRef) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(textRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(text = LEGEND_TEXT,
                style = TitleMainScreen
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(imageRef) {
                    bottom.linkTo(parent.bottom, margin = 6.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            if (selectedCurrencyImage != null) {
                Image(
                    painter = painterResource(id = selectedCurrencyImage),
                    contentDescription = selectedCurrency + LOGO_DESCRIPTION,
                    modifier = Modifier
                        .size(160.dp)
                )
            }
        }
        Box (
            modifier = Modifier.constrainAs(columnRef){
                start.linkTo(parent.start)
                top.linkTo(textRef.bottom)
                end.linkTo(parent.end)
            }
                .padding(8.dp)
        ) {
            Column {
                predictedValue?.let {
                    val roundedPredictedValue = String.format("%.2f", it)
                    Text(text = "Сглаженный прогноз: ${today.plusDays(1)}: $roundedPredictedValue")
                } ?: Text(text = "Нет прогноза")
            }
        }
    }
}