package com.example.millionaire.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.millionaire.utils.Constants.EXTRA_COUNT_MONEY
import com.example.millionaire.utils.Constants.EXTRA_IS_FINISH_GAME
import com.example.millionaire.utils.Constants.EXTRA_LEVEL
import com.example.millionaire.utils.Constants.UNKNOWN

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    startDestinationScreen: Screen,
    mainScreenContent: @Composable () -> Unit,
    loginScreenContent: @Composable () -> Unit,
    gameScreenContent: @Composable () -> Unit,
    recordsScreenContent: @Composable () -> Unit,
    resultScreenContent: @Composable (Boolean, Int, Int) -> Unit,
    rulesScreenContent: @Composable () -> Unit,
    finishGameScreenContent: @Composable (Int, Int) -> Unit
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
        composable(
            route =
            "${Screen.ResultScreen.route}/{$EXTRA_IS_FINISH_GAME}/{$EXTRA_LEVEL}/{$EXTRA_COUNT_MONEY}",
            arguments = listOf(
                navArgument(EXTRA_IS_FINISH_GAME) {
                    type = NavType.BoolType
                },
                navArgument(EXTRA_LEVEL) {
                    type = NavType.IntType
                },
                navArgument(EXTRA_COUNT_MONEY) {
                    type = NavType.IntType
                },
            )
        ) { backStackEntry ->
            resultScreenContent(
                backStackEntry.arguments?.getBoolean(EXTRA_IS_FINISH_GAME) ?: false,
                backStackEntry.arguments?.getInt(EXTRA_LEVEL) ?: UNKNOWN,
                backStackEntry.arguments?.getInt(EXTRA_COUNT_MONEY) ?: UNKNOWN
            )
        }
        composable(route = Screen.RulesScreen.route) {
            rulesScreenContent()
        }
        composable(
            route = "${Screen.FinishGameScreen.route}/{$EXTRA_LEVEL}/{$EXTRA_COUNT_MONEY}",
            arguments = listOf(
                navArgument(EXTRA_LEVEL) {
                    type = NavType.IntType
                },
                navArgument(EXTRA_COUNT_MONEY) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            finishGameScreenContent(
                backStackEntry.arguments?.getInt(EXTRA_LEVEL) ?: UNKNOWN,
                backStackEntry.arguments?.getInt(EXTRA_COUNT_MONEY) ?: UNKNOWN
            )
        }
    }
}