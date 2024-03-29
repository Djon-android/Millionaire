package com.example.millionaire.presentation.app_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.millionaire.navigation.AppNavGraph
import com.example.millionaire.navigation.Screen
import com.example.millionaire.navigation.rememberNavigationState
import com.example.millionaire.presentation.finish_game_screen.FinishGameScreen
import com.example.millionaire.presentation.game_screen.GameScreen
import com.example.millionaire.presentation.login_screen.LoginScreen
import com.example.millionaire.presentation.main_screen.MainScreen
import com.example.millionaire.presentation.records_screen.RecordsScreen
import com.example.millionaire.presentation.result_screen.ResultScreen
import com.example.millionaire.presentation.ui.theme.MillionaireTheme

@Composable
fun App() {
    MillionaireTheme(
        dynamicColor = false
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppGraph()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
                navigationToRecords = {
                    navigationState.navigateTo(Screen.RecordsScreen.route)
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
            GameScreen(
                navigationToResultScreen = { isFinish, level, countMoney ->
                    navigationState.navigateTo(
                        "${Screen.ResultScreen.route}/$isFinish/$level/$countMoney"
                    )
                },
                navigationToFinishScreen = { level, countMoney, isLosing ->
                    navigationState.navigateTo(
                        "${Screen.FinishGameScreen.route}/$level/$countMoney/$isLosing"
                    )
                },
                navigationToBack = {
                    navigationState.navigateToBack()
                }
            )
        },
        recordsScreenContent = {
            RecordsScreen(
                navigateToMainScreen = {
                    navigationState.navigateToBack()
                }
            )
        },
        resultScreenContent = { isFinishGame, level, countMoney ->
            ResultScreen(
                isFinishGame = isFinishGame,
                level = level,
                countMoney = countMoney,
                navigationToBack = {
                    navigationState.navigateToBack()
                },
                navigationToFinishGame = { levelExtra, countMoneyExtra, isLosing ->
                    navigationState.navigateTo(
                        "${Screen.FinishGameScreen.route}/$levelExtra/$countMoneyExtra/$isLosing"
                    )
                }
            )
        },
        finishGameScreenContent = { level, countMoney, isLosing ->
            FinishGameScreen(
                level = level,
                countMoney = countMoney,
                isLosing = isLosing,
                navigateToLoginScreen = {
                    navigationState.resetNavigation()
                    navigationState.navigateTo(Screen.LoginScreen.route)
                },
                navigateToMainScreen = {
                    navigationState.resetNavigation()
                }
            )
        }
    )
}