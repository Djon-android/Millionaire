package com.example.millionaire.presentation.result_screen


import android.media.MediaPlayer
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.millionaire.R
import com.example.millionaire.presentation.result_screen.states.NavigationFromResultState
import com.example.millionaire.presentation.result_screen.states.SaveResultDialogState
import com.example.millionaire.presentation.ui.theme.BlueGradient
import com.example.millionaire.presentation.ui.theme.Gray
import com.example.millionaire.presentation.ui.theme.GreenGradient
import com.example.millionaire.presentation.ui.theme.LightBlueGradient
import com.example.millionaire.presentation.ui.theme.OrangeGradient
import com.example.millionaire.presentation.ui.theme.Typography
import com.example.millionaire.presentation.ui.theme.White

@Composable
fun ResultScreen(
    viewModel: ResultViewModel = hiltViewModel(),
    isFinishGame: Boolean,
    level: Int,
    countMoney: Int,
    navigationToFinishGame: (Int, Int, Boolean) -> Unit,
    navigationToBack: () -> Unit
) {

    val dialogState =
        viewModel.saveResultDialogFlow.collectAsStateWithLifecycle(initialValue = SaveResultDialogState.Initial)

    Dialogs(
        dialogState,
        viewModel::dismissDialog,
        viewModel::saveResult
    )

    val navigationState =
        viewModel.navigationFlow.collectAsStateWithLifecycle(initialValue = NavigationFromResultState.Initial)

    Navigation(
        navigationState,
        navigationToFinishGame,
        level,
        countMoney
    )

    PlayMusicOnEntry(isFinishGame = isFinishGame, level = level)

    BackHandler(
        enabled = isFinishGame
    ) {
        navigationToFinishGame(level, countMoney, false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = navigationToBack)
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .zIndex(5f)
        ) {
            Icon(
                modifier = Modifier
                    .padding(start = 20.dp, top = 12.dp)
                    .clickable(onClick = viewModel::getMoney),
                imageVector = ImageVector.vectorResource(id = R.drawable.get_money),
                contentDescription = null,
                tint = White
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.logo),
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.TopCenter)
                    .padding(top = 32.dp)
                    .shadow(elevation = 20.dp, shape = CircleShape)
            )
        }

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp, bottom = 16.dp, start = 32.dp, end = 32.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(viewModel.sums) {
                val background = remember {
                    when {
                        level == it.first -> Brush.verticalGradient(GreenGradient)
                        it.first == 5 || it.first == 10 -> Brush.verticalGradient(LightBlueGradient)
                        it.first == 15 -> Brush.verticalGradient(OrangeGradient)
                        else -> Brush.verticalGradient(BlueGradient)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .border(BorderStroke(3.dp, White), shape = CutCornerShape(50))
                        .background(background, shape = CutCornerShape(50))
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${it.first}:",
                        style = Typography.titleLarge.copy(
                            lineHeight = 21.48.sp
                        ),
                        color = White
                    )
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = it.second,
                        style = Typography.titleLarge.copy(
                            lineHeight = 21.48.sp
                        ),
                        color = White,
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun PlayMusicOnEntry(isFinishGame: Boolean, level: Int) {
    val context = LocalContext.current
    val mediaPlayer =
        when {
            level == 15 -> remember { MediaPlayer.create(context, R.raw.million_sound) }
            isFinishGame -> remember { MediaPlayer.create(context, R.raw.soundoflose) }
            else -> remember { MediaPlayer.create(context, R.raw.verniyotvet) }
        }

    LaunchedEffect(Unit) {
        mediaPlayer.start()
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}

@Composable
fun Dialogs(
    state: State<SaveResultDialogState>,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    when (state.value) {
        SaveResultDialogState.DialogSaveResult -> {
            AlertDialog(
                onDismissRequest = onDismiss,
                title = {
                    Text(text = stringResource(R.string.save_result))
                },
                text = {
                    Text(text = stringResource(R.string.are_you_sure_save_result))
                },
                dismissButton = {
                    Button(onClick = onDismiss) {
                        Text(stringResource(R.string.not))
                    }
                },
                confirmButton = {
                    Button(onClick = onSuccess) {
                        Text(stringResource(R.string.save))
                    }
                },
                containerColor = Gray,
                titleContentColor = White,
                textContentColor = White
            )
        }

        SaveResultDialogState.Initial -> {}
    }
}

@Composable
fun Navigation(
    state: State<NavigationFromResultState>,
    navigationToFinishScreen: (Int, Int, Boolean) -> Unit,
    level: Int,
    countMoney: Int
) {
    when (state.value) {

        NavigationFromResultState.Initial -> {}
        NavigationFromResultState.NavigationToFinishScreen -> {
            navigationToFinishScreen(level, countMoney, false)
        }
    }
}