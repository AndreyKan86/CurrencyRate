package com.example.currencyrate.ui.composables.scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyrate.ui.composables.components.BottomButton
import com.example.currencyrate.ui.composables.components.CurrencyDropdown
import com.example.currencyrate.ui.composables.components.TimeDropdown
import com.example.currencyrate.ui.viewmodels.CurrencyViewModel

//Нижнее меню
@Composable
fun BottomMenu(currencyViewModel: CurrencyViewModel = viewModel())
{
    ConstraintLayout (modifier = Modifier
        .fillMaxSize()
    ) {
        val (box1, box2, box3) = createRefs()

        Box(modifier = Modifier.constrainAs(box1){
            top.linkTo(parent.top, margin = 12.dp)
            start.linkTo(parent.start, margin = 24.dp)
        }){
            CurrencyDropdown()
        }

        Box(modifier = Modifier.constrainAs(box2){
            top.linkTo(parent.top, margin = 12.dp)
            start.linkTo(parent.start, margin = 160.dp)
        }){
            TimeDropdown(currencyViewModel)
        }

        Box(modifier = Modifier.constrainAs(box3){
            top.linkTo(parent.top, margin = 8.dp)
            end.linkTo(parent.end, margin = 24.dp)
        })
        {
            BottomButton(currencyViewModel)
        }
    }
}
