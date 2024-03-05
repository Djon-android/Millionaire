package com.example.millionaire.navigation

import androidx.compose.runtime.Composable

sealed class Screen(val route: String) {
    data object MainScreen : Screen("main_screen")
    data object LoginScreen : Screen("login_screen")
    data object GameScreen : Screen("game_screen")
    data object RecordsScreen : Screen("records_screen")
    data object ResultScreen : Screen("result_screen")
    data object RulesScreen : Screen("rules_screen")
    data object FinishGameScreen : Screen("finish_game_screen")
}