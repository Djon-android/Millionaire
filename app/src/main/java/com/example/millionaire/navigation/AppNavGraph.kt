package com.example.millionaire.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    startDestinationScreen: Screen,
    mainScreenContent: @Composable () -> Unit,
    loginScreenContent: @Composable () -> Unit,
    gameScreenContent: @Composable () -> Unit,
    recordsScreenContent: @Composable () -> Unit,
    resultScreenContent: @Composable () -> Unit,
    rulesScreenContent: @Composable () -> Unit
) {
    NavHost(navController = navHostController, startDestination = startDestinationScreen.route) {
        composable(route = Screen.MainScreen.route) {
            mainScreenContent()
        }
        composable(route = Screen.LoginScreen.route) {
            loginScreenContent()
        }
        composable(route = Screen.GameScreen.route) {
            gameScreenContent()
        }
        composable(route = Screen.RecordsScreen.route) {
            recordsScreenContent()
        }
        composable(route = Screen.ResultScreen.route) {
            resultScreenContent()
        }
        composable(route = Screen.RulesScreen.route) {
            rulesScreenContent()
        }
    }
}