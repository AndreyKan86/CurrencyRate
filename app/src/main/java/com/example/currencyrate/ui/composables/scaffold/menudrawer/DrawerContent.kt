package com.example.currencyrate.ui.composables.scaffold.menudrawer

import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyrate.R
import com.example.currencyrate.ui.composables.components.ThemeSwitcher
import com.example.currencyrate.ui.theme.COMPANY_NAME
import com.example.currencyrate.ui.theme.DESCRIPTION_APP
import com.example.currencyrate.ui.theme.LOGO_DESCRIPTION
import com.example.currencyrate.ui.theme.modifiers.logoImageModifier
import com.example.currencyrate.ui.theme.styles.CompanyNameTextStyle
import com.example.currencyrate.ui.viewmodels.CurrencyViewModel

//Содержимое выплывающего меню
@Composable
fun DrawerContent(onItemClick: () -> Unit)
{
    val uriHandler = LocalUriHandler.current
    val viewModel: CurrencyViewModel = viewModel()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    ModalDrawerSheet()
    {
        ConstraintLayout()
        {
            val (logo, nameCompany, upDivider, bottomDivider, textDescription, themeswitch) = createRefs()

            Box(
                modifier = Modifier.constrainAs(themeswitch) {
                    top.linkTo(parent.top, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
            ) {
                ThemeSwitcher(
                    isDarkTheme = !isDarkTheme, // Используем полученное значение
                    onThemeChanged = { viewModel.toggleTheme() }
                )
            }
            Box(
                modifier = Modifier.constrainAs(logo){
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = LOGO_DESCRIPTION,
                    modifier = Modifier.logoImageModifier(uriHandler)
                )
            }

            Box(
                modifier = Modifier.constrainAs(nameCompany)
                {
                    top.linkTo(parent.top, margin = 40.dp)
                    start.linkTo(logo.end, margin = 16.dp)
                }
            )
            {
                Text(
                    text = COMPANY_NAME,
                    style = CompanyNameTextStyle
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(upDivider) {
                        top.linkTo(parent.top, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                color = Color.DarkGray,
                thickness = 2.dp
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(bottomDivider)
                    {
                        top.linkTo(logo.bottom, margin = 6.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                color = Color.DarkGray,
                thickness = 2.dp
            )

            Box(modifier = Modifier
                .constrainAs(textDescription)
                {
                    top.linkTo(bottomDivider.top, margin = 12.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(6.dp)
            )
            {
                Text(text = DESCRIPTION_APP)
            }
        }
    }
}
