package com.example.millionaire.presentation.result_screen

import androidx.compose.runtime.Composable

@Composable
fun ResultScreen(
    isFinishGame: Boolean,
    level: Int,
    countMoney: Int,
    navigationToFinishGame: (Int, Int) -> Unit,
    navigationToBack: () -> Unit
) {
}