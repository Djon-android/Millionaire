package com.example.millionaire.presentation.app_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.millionaire.navigation.AppNavGraph
import com.example.millionaire.navigation.Screen
import com.example.millionaire.navigation.rememberNavigationState
import com.example.millionaire.presentation.game_screen.GameScreen
import com.example.millionaire.presentation.login_screen.LoginScreen
import com.example.millionaire.presentation.main_screen.MainScreen
import com.example.millionaire.presentation.records_screen.RecordsScreen
import com.example.millionaire.presentation.result_screen.ResultScreen
import com.example.millionaire.presentation.rules_screen.RulesScreen
import com.example.millionaire.presentation.ui.theme.MillionaireTheme

@Composable
fun App() {
    MillionaireTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppGraph()
        }
    }
}

@Composable
fun AppGraph() {
    val navigationState = rememberNavigationState()
    AppNavGraph(
        navHostController = navigationState.navHostController,
        startDestinationScreen = Screen.MainScreen,
        mainScreenContent = {
            MainScreen(
                navigationToLogin = {
                    navigationState.navigateTo(Screen.LoginScreen.route)
                },
                navigationToResults = {
                    navigationState.navigateTo(Screen.ResultScreen.route)
                },
                navigationToRules = {
                    navigationState.navigateTo(Screen.RulesScreen.route)
                }
            )
        },
        loginScreenContent = {
            LoginScreen(
                navigateToGame = {
                    navigationState.navigateTo(Screen.GameScreen.route)
                }
            )
        },
        gameScreenContent = {
            GameScreen()
        },
        recordsScreenContent = {
            RecordsScreen()
        },
        resultScreenContent = {
            ResultScreen()
        },
        rulesScreenContent = {
            RulesScreen()
        }
    )
}