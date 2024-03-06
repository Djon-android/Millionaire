package com.example.millionaire.presentation.game_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.millionaire.R

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    navigationToResultScreen: (Boolean, Int, Int) -> Unit,
    navigationToFinishScreen: (Int, Int) -> Unit
) {
    val uiState =
        viewModel.uiState.collectAsStateWithLifecycle(initialValue = QuestionsState.Initial)

    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
            .statusBarsPadding()
            .safeContentPadding()
    ) {
        GameScreenContent(
            uiState = uiState,
            viewModel = viewModel,
            navigationToResultScreen = navigationToResultScreen,
            navigationToFinishScreen = navigationToFinishScreen
        )
    }
}

@Composable
fun GameScreenContent(
    uiState: State<QuestionsState>,
    viewModel: GameViewModel,
    navigationToResultScreen: (Boolean, Int, Int) -> Unit,
    navigationToFinishScreen: (Int, Int) -> Unit
) {

}