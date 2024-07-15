package com.example.workmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workmanager.ui.navigation.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.Main.route) {
                composable(Screen.Main.route) { MainScreen(navController) }
                composable(Screen.OneTimeWork.route) { OneTimeWorkScreen() }
                composable(Screen.PeriodicWorkRequest.route) { PeriodicWorkRequestScreen() }
                composable(Screen.ChainWorkScreen.route) { ChainWorkScreen() }
            }
        }
    }
}