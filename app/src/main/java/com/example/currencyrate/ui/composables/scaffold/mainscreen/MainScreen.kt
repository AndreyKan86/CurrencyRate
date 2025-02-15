package com.example.currencyrate.ui.composables.scaffold.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyrate.ui.composables.scaffold.mainscreen.chart.CurrencyGraf
import com.example.currencyrate.ui.composables.scaffold.mainscreen.datascreen.CurrencyRateList
import com.example.currencyrate.ui.composables.scaffold.mainscreen.legend.Legend
import com.example.currencyrate.ui.theme.CURRENCY_LABEL
import com.example.currencyrate.ui.theme.TIME_LABEL
import com.example.currencyrate.ui.theme.modifiers.customDivider
import com.example.currencyrate.ui.theme.modifiers.mainScreenBackground
import com.example.currencyrate.ui.theme.modifiers.topRightText
import com.example.currencyrate.ui.theme.styles.TitleMainScreen
import com.example.currencyrate.ui.viewmodels.CurrencyViewModel

//Главный экран
@Composable
fun MainScreen(currencyViewModel: CurrencyViewModel = viewModel(), modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier.mainScreenBackground()
    )
    {
        val (boxTopLeft, boxTopRight, boxBottom, horizontalDivider, verticalDivider, rightTopText) = createRefs()
        val horizontalGuideline = createGuidelineFromTop(0.5f)
        val verticalGuideline = createGuidelineFromStart(0.5f)

        val selectedCurrency by currencyViewModel.selectedCurrency.collectAsState()
        val selectedTimeInterval by currencyViewModel.selectedTimeInterval.collectAsState()

        // Текст в верхнем правом углу
        Box(
            modifier = Modifier
                .constrainAs(rightTopText) {
                    top.linkTo(parent.top)
                    start.linkTo(verticalGuideline)
                    end.linkTo(parent.end)
                }
                .topRightText(),
            contentAlignment = Alignment.Center
        )
        {
            Text(
                text = CURRENCY_LABEL + selectedCurrency + "\n" + TIME_LABEL + selectedTimeInterval,
                style = TitleMainScreen
            )
        }

        // Box для списка валют
        Box(
            modifier = Modifier.constrainAs(boxTopRight) {
                top.linkTo(rightTopText.bottom)
                bottom.linkTo(horizontalGuideline)
                start.linkTo(verticalGuideline)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            CurrencyRateList()
        }

        // Горизонтальный разделитель
        Box(
            modifier = Modifier
                .constrainAs(horizontalDivider) {
                    top.linkTo(horizontalGuideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .customDivider()
        ) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.DarkGray
            )
        }

        // Вертикальный разделитель
        Box(
            modifier = Modifier.constrainAs(verticalDivider) {
                top.linkTo(parent.top)
                bottom.linkTo(horizontalDivider.top)
                start.linkTo(verticalGuideline)
                height = Dimension.fillToConstraints
            }
        ) {
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                color = Color.DarkGray
            )
        }

        // Box для легенды
        Box(
            modifier = Modifier.constrainAs(boxTopLeft) {
                top.linkTo(parent.top)
                bottom.linkTo(horizontalDivider.top)
                start.linkTo(parent.start)
                end.linkTo(verticalGuideline)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            Legend()
        }

        // Box для графика
        Box(
            modifier = Modifier.constrainAs(boxBottom) {
                top.linkTo(horizontalDivider.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            CurrencyGraf()
        }
    }
}

