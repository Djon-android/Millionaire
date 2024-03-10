package com.example.millionaire.presentation.game_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.millionaire.R
import com.example.millionaire.presentation.common_components.CutButton
import com.example.millionaire.presentation.game_screen.items.AnswerItem
import com.example.millionaire.presentation.game_screen.items.TipsItem
import com.example.millionaire.presentation.game_screen.states.NavigationFromGameState
import com.example.millionaire.presentation.game_screen.states.QuestionsState
import com.example.millionaire.presentation.game_screen.states.TipsDialogState
import com.example.millionaire.presentation.ui.theme.BlueGradient
import com.example.millionaire.presentation.ui.theme.DarkRed
import com.example.millionaire.presentation.ui.theme.Gray
import com.example.millionaire.presentation.ui.theme.GreenGradient
import com.example.millionaire.presentation.ui.theme.LightBlueGradient
import com.example.millionaire.presentation.ui.theme.Orange
import com.example.millionaire.presentation.ui.theme.OrangeGradient
import com.example.millionaire.presentation.ui.theme.Red
import com.example.millionaire.presentation.ui.theme.RedGradient
import com.example.millionaire.presentation.ui.theme.Typography
import com.example.millionaire.presentation.ui.theme.White
import com.example.millionaire.presentation.ui.theme.Yellow
import com.example.millionaire.utils.Constants.TIPS_ID_50_50
import com.example.millionaire.utils.Constants.TIPS_ID_CALL
import kotlinx.collections.immutable.ImmutableList

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    navigationToResultScreen: (Boolean, Int, Int) -> Unit,
    navigationToFinishScreen: (Int, Int, Boolean) -> Unit,
    navigationToBack: () -> Unit
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.resumeScreen()
            }
            if (event == Lifecycle.Event.ON_STOP) {
                viewModel.stopScreen()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val tipsDialogState =
        viewModel.tipsDialogFlow.collectAsStateWithLifecycle(TipsDialogState.Initial)

    TipsDialog(
        tipsState = tipsDialogState,
        onDismiss = viewModel::dismissDialogs
    )

    val navigationState =
        viewModel.navigationFlow.collectAsStateWithLifecycle(NavigationFromGameState.Initial)

    Navigation(
        navigationState = navigationState,
        navigationToResultScreen = navigationToResultScreen,
        navigationToFinishScreen = navigationToFinishScreen,
    )

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
            .navigationBarsPadding()
    ) {
        GameScreenContent(
            uiState = uiState,
            viewModel = viewModel,
            navigationToBack = navigationToBack
        )
    }
}

@Composable
fun TipsDialog(
    tipsState: State<TipsDialogState>,
    onDismiss: () -> Unit
) {
    when (val currentState = tipsState.value) {
        is TipsDialogState.DialogTypeAudience -> {
            Dialogs(
                onDismiss = onDismiss,
                text = currentState.text,
                tipDescriptionResId = R.string.audience_desctiption
            )
        }

        is TipsDialogState.DialogTypeCall -> {
            Dialogs(
                onDismiss = onDismiss,
                text = currentState.text,
                tipDescriptionResId = R.string.call_description
            )
        }

        is TipsDialogState.Initial -> {}
    }
}

@Composable
fun Navigation(
    navigationState: State<NavigationFromGameState>,
    navigationToResultScreen: (Boolean, Int, Int) -> Unit,
    navigationToFinishScreen: (Int, Int, Boolean) -> Unit,
) {
    when (val currentState = navigationState.value) {
        is NavigationFromGameState.NavigationToResultScreen -> {
            navigationToResultScreen(
                currentState.isFinish,
                currentState.level,
                currentState.countMoney
            )
        }

        is NavigationFromGameState.NavigationToFinishScreen -> {
            navigationToFinishScreen(
                currentState.level,
                currentState.countMoney,
                true
            )
        }

        else -> {}
    }
}

@Composable
fun GameScreenContent(
    uiState: State<QuestionsState>,
    viewModel: GameViewModel,
    navigationToBack: () -> Unit
) {
    when (uiState.value) {
        is QuestionsState.Initial -> {}
        is QuestionsState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is QuestionsState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                CutButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            Brush.verticalGradient(LightBlueGradient),
                            shape = CutCornerShape(50)
                        ),
                    onClick = viewModel::reloadData
                ) {
                    Text(
                        text = stringResource(id = R.string.update),
                        style = Typography.labelLarge
                    )
                }
            }
        }

        is QuestionsState.Questions -> {
            Game(
                navigationToBack = navigationToBack,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun Game(
    navigationToBack: () -> Unit,
    viewModel: GameViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ActionBar(
            navigationToBack = navigationToBack,
            numberOfQuestion = viewModel.numberOfQuestion,
            questionMoney = viewModel.questionMoney,
            resetQuestions = viewModel::resetQuestions
        )
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Timer(viewModel.timer)

            Spacer(modifier = Modifier.height(32.dp))

            Question(viewModel.question)

            Spacer(modifier = Modifier.height(32.dp))
            Spacer(modifier = Modifier.weight(1f))

            Answers(
                answers = viewModel.answers,
                onClickToAnswer = viewModel::answerOnQuestion,
                variantAnswerList = viewModel.variantAnswerList
            )

            Spacer(modifier = Modifier.height(36.dp))

            Tips(
                viewModel.tips,
                viewModel::enabledTips
            )

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun ActionBar(
    navigationToBack: () -> Unit,
    resetQuestions: () -> Unit,
    numberOfQuestion: State<Int>,
    questionMoney: State<Int>
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .size(32.dp),
            onClick = {
                if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                    navigationToBack()
                    resetQuestions()
                }
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                contentDescription = stringResource(R.string.back),
                tint = Color.White
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.question, numberOfQuestion.value),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = Typography.titleMedium.copy(
                    color = Color.White.copy(alpha = 0.5f)
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.sum, questionMoney.value),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = Typography.titleLarge.copy(
                    color = Color.White
                )
            )
        }
        Box(modifier = Modifier.size(32.dp))
    }
}

@Composable
fun Timer(
    time: State<Int>
) {
    val textColor = remember {
        derivedStateOf {
            when {
                time.value >= 20 -> {
                    White
                }

                time.value > 10 -> {
                    Yellow
                }

                else -> {
                    Red
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .width(88.dp)
            .height(45.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(
                when {
                    time.value >= 20 -> {
                        Color.White.copy(alpha = 0.1f)
                    }

                    time.value > 10 -> {
                        Orange.copy(alpha = 0.3f)
                    }

                    else -> {
                        DarkRed.copy(alpha = 0.5f)
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.stopwatch),
                contentDescription = stringResource(R.string.timer),
                tint = textColor.value
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = time.value.toString().padStart(2, '0'),
                style = Typography.bodyLarge,
                color = textColor.value
            )
        }
    }
}

@Composable
fun Question(
    question: State<String>
) {
    Text(
        text = question.value,
        style = Typography.bodyLarge.copy(
            lineHeight = 28.64.sp
        ),
        color = White,
        textAlign = TextAlign.Center
    )
}

@Composable
fun Answers(
    answers: State<ImmutableList<AnswerItem>>,
    onClickToAnswer: (String) -> Unit,
    variantAnswerList: ImmutableList<String>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        answers.value.forEachIndexed { index, answerItem ->

            val background = remember(answerItem) {
                when {
                    answerItem.isRightAnswer -> {
                        Brush.verticalGradient(GreenGradient)
                    }

                    answerItem.isError -> {
                        Brush.verticalGradient(RedGradient)
                    }

                    answerItem.isSelected -> {
                        Brush.verticalGradient(OrangeGradient)
                    }

                    else -> {
                        Brush.verticalGradient(BlueGradient)
                    }
                }
            }

            CutButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(background, shape = CutCornerShape(50)),
                onClick = { onClickToAnswer(answerItem.title) },
                enabled = answerItem.enabled
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${variantAnswerList[index]}:",
                        style = Typography.titleLarge,
                        color = Orange
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (answerItem.isDisabled) "" else answerItem.title,
                        style = Typography.titleLarge,
                        color = White,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
fun Tips(
    tips: State<ImmutableList<TipsItem>>,
    onClickToTips: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        tips.value.forEach {
            val painterResId = remember {
                when (it.id) {
                    TIPS_ID_50_50 -> {
                        R.drawable.tips_50_50
                    }

                    TIPS_ID_CALL -> {
                        R.drawable.tips_call
                    }

                    else -> {
                        R.drawable.tips_audience
                    }
                }
            }
            val modifier = remember(it) {
                if (it.isActive) {
                    Modifier
                } else {
                    Modifier.alpha(0.2f)
                }
            }
            Image(
                modifier = modifier
                    .clickable {
                        if (it.isActive) {
                            onClickToTips(it.id)
                        }
                    },
                painter = painterResource(id = painterResId),
                contentDescription = null
            )
        }
    }
}

@Composable
fun Dialogs(
    onDismiss: () -> Unit,
    text: String,
    tipDescriptionResId: Int
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Gray,
                contentColor = White
            )

        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(tipDescriptionResId),
                    style = Typography.bodyLarge.copy(
                        lineHeight = 28.64.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = text,
                    style = Typography.bodyLarge.copy(
                        lineHeight = 28.64.sp
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                CutButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            Brush.verticalGradient(LightBlueGradient),
                            shape = CutCornerShape(50)
                        ),
                    onClick = onDismiss
                ) {
                    Text(
                        text = stringResource(R.string.ok),
                        style = Typography.labelLarge
                    )
                }
            }
        }
    }
}