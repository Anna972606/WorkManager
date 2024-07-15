package com.example.workmanager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.workmanager.ui.navigation.Screen

const val MAIN_SCREEN_TAG = "MainScreen"

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.dimen_size_16dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                navController.navigate(Screen.OneTimeWork.route)
            }
        ) {
            Text(
                text = stringResource(id = R.string.one_time_work_request_builder)
            )
        }
        Button(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.dimen_size_16dp)),
            onClick = {
                navController.navigate(Screen.PeriodicWorkRequest.route)
            }
        ) {
            Text(
                text = stringResource(id = R.string.periodic_work_request)
            )
        }
        Button(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.dimen_size_16dp)),
            onClick = {
                navController.navigate(Screen.ChainWorkScreen.route)
            }
        ) {
            Text(
                text = stringResource(id = R.string.chain_work_screen)
            )
        }
    }
}