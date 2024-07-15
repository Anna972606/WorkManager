package com.example.workmanager.ui.navigation

import com.example.workmanager.CHAIN_WORK_SCREEN_TAG
import com.example.workmanager.MAIN_SCREEN_TAG
import com.example.workmanager.ONE_TIME_WORK_SCREEN_TAG
import com.example.workmanager.PERIODIC_WORK_REQUEST_TAG

sealed class Screen(val route: String) {
    object Main : Screen(MAIN_SCREEN_TAG)
    object OneTimeWork : Screen(ONE_TIME_WORK_SCREEN_TAG)
    object PeriodicWorkRequest : Screen(PERIODIC_WORK_REQUEST_TAG)
    object ChainWorkScreen : Screen(CHAIN_WORK_SCREEN_TAG)
}