package com.example.millionaire.presentation.game_screen

import android.app.Application
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.millionaire.R
import com.example.millionaire.domain.model.Question
import com.example.millionaire.domain.usecases.GetQuestionsUseCase
import com.example.millionaire.domain.usecases.ReloadQuestionsUseCase
import com.example.millionaire.domain.usecases.ResetQuestionsUseCase
import com.example.millionaire.presentation.game_screen.items.AnswerItem
import com.example.millionaire.presentation.game_screen.items.TipsItem
import com.example.millionaire.presentation.game_screen.states.NavigationFromGameState
import com.example.millionaire.presentation.game_screen.states.QuestionsState
import com.example.millionaire.presentation.game_screen.states.TipsDialogState
import com.example.millionaire.utils.Constants.TIPS_ID_50_50
import com.example.millionaire.utils.Constants.TIPS_ID_AUDIENCE
import com.example.millionaire.utils.Constants.TIPS_ID_CALL
import com.example.millionaire.utils.Constants.TIPS_ID_MISTAKE
import com.example.millionaire.utils.LoadResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "GameViewModel"

@HiltViewModel
class GameViewModel @Inject constructor(
    getQuestionsUseCase: GetQuestionsUseCase,
    private val reloadQuestionsUseCase: ReloadQuestionsUseCase,
    private val resetQuestionsUseCase: ResetQuestionsUseCase,
    private val application: Application
) : ViewModel() {

    private var userSum = 0
    private var level = 0
    private var gameEnd = false
    private var gameStarted = false
    private var gamePause = false
    private var gameAnswered = false
    private var isRightAnswer = false
    private var delayBeforeShowRightAnswer = 5
    private var userAnswer = ""
    private var needNewQuestion = false
    private var tipsMistake = false
    private var _questions = mutableListOf<Question>()
    private var jobTimer: Job = Job()
    private var mediaPlayer = MediaPlayer.create(application, R.raw.clock_sound)

    private val _numberOfQuestion = mutableIntStateOf(1)
    val numberOfQuestion: State<Int>
        get() = _numberOfQuestion

    private val _questionMoney = mutableIntStateOf(100)
    val questionMoney: State<Int>
        get() = _questionMoney

    private val _timer = mutableIntStateOf(30)
    val timer: State<Int>
        get() = _timer

    private val _question =
        mutableStateOf("")
    val question: State<String>
        get() = _question

    private val _answers: MutableState<ImmutableList<AnswerItem>> =
        mutableStateOf(persistentListOf())
    val answers: State<ImmutableList<AnswerItem>>
        get() = _answers

    private val _tips: MutableState<ImmutableList<TipsItem>> = mutableStateOf(
        persistentListOf(
            TipsItem(TIPS_ID_50_50),
            TipsItem(TIPS_ID_MISTAKE),
            TipsItem(TIPS_ID_AUDIENCE)
        )
    )
    val tips: State<ImmutableList<TipsItem>>
        get() = _tips

    val variantAnswerList: ImmutableList<String> = persistentListOf("A", "B", "C", "D")

    private val _navigationFlow = MutableSharedFlow<NavigationFromGameState>()
    val navigationFlow = _navigationFlow.asSharedFlow()

    private val _tipsDialogFlow = MutableSharedFlow<TipsDialogState>()
    val tipsDialogFlow = _tipsDialogFlow.asSharedFlow()

    private val questionsFlow = getQuestionsUseCase()

    val uiState = questionsFlow
        .map { result ->
            when (result) {
                is LoadResource.Success -> {
                    if (_questions.isEmpty()) {
                        _questions.addAll(result.data?.toImmutableList() ?: persistentListOf())
                        startGame()
                    }
                    QuestionsState.Questions(
                        questions = result.data?.toImmutableList() ?: persistentListOf()
                    ) as QuestionsState

                }

                is LoadResource.Error -> {
                    QuestionsState.Error as QuestionsState
                }

                is LoadResource.Loading -> {
                    QuestionsState.Loading as QuestionsState
                }
            }
        }
        .onStart { emit(QuestionsState.Loading) }


    fun reloadData() {
        viewModelScope.launch {
            reloadQuestionsUseCase.reloadData()
        }
    }

    fun resumeScreen() {
        Log.d(TAG, "resumeScreen")
        if (needNewQuestion) {
            startGame()
            return
        }
        if (gamePause) {
            gamePause = false
            resumeGame()
            return
        }
        if (_questions.isNotEmpty()) {
            startGame()
            return
        }
    }

    fun stopScreen() {
        Log.d(TAG, "stopScreen")
        soundControl(needPause = true)
        gamePause = true
        pauseGame()
    }

    fun answerOnQuestion(answer: String) {
        Log.d(TAG, "answerOnQuestion")
        viewModelScope.launch {
            gameAnswered = true
            userAnswer = answer
            val question = _questions[level]
            isRightAnswer = userAnswer == question.correctAnswer
            if (tipsMistake) {
                isRightAnswer = true
                tipsMistake = false
            }
            userSum = calculateSum(isRightAnswer)
            _answers.value = _answers.value.map {
                it.copy(
                    isSelected = it.title == answer,
                    enabled = false
                )
            }.toImmutableList()
            stopTimer()
            soundControl(R.raw.answered_on_question)
            handleAnswer()
        }
    }

    fun enabledTips(tipId: String) {
        _tips.value = _tips.value.map {
            if (it.id == tipId) {
                it.copy(isActive = false)
            } else {
                it
            }
        }.toImmutableList()
        val question = _questions[level]
        when (tipId) {
            TIPS_ID_50_50 -> {
                if (!_questions.indices.contains(level)) {
                    return
                }
                val incorrectAnswers = question.incorrectAnswers
                val twoIncorrectAnswers = incorrectAnswers.dropLast(1)
                _answers.value = _answers.value.map {
                    if (it.title in twoIncorrectAnswers) {
                        it.copy(
                            enabled = false,
                            isDisabled = true
                        )
                    } else {
                        it
                    }
                }.toImmutableList()
            }

            TIPS_ID_CALL -> {
                var callGetRightAnswer = false
                if (Math.random() * 100 < 80) {
                    callGetRightAnswer = true
                }
                val result = if (callGetRightAnswer) {
                    question.correctAnswer
                } else {
                    question.incorrectAnswers.first()
                }
                viewModelScope.launch {
                    _tipsDialogFlow.emit(TipsDialogState.DialogTypeCall(result))
                }
            }

            TIPS_ID_AUDIENCE -> {
                var audienceGetRightAnswer = false
                if (Math.random() * 100 < 70) {
                    audienceGetRightAnswer = true
                }
                val result = if (audienceGetRightAnswer) {
                    question.correctAnswer
                } else {
                    question.incorrectAnswers.first()
                }
                viewModelScope.launch {
                    _tipsDialogFlow.emit(TipsDialogState.DialogTypeAudience(result))
                }
            }

            TIPS_ID_MISTAKE -> {
                tipsMistake = true
            }
        }
    }

    fun dismissDialogs() {
        viewModelScope.launch {
            _tipsDialogFlow.emit(TipsDialogState.Initial)
        }
    }

    fun resetQuestions() {
        resetQuestionsUseCase()
    }

    private fun startGame() {
        Log.d(TAG, "startGame")
        if (gameStarted || gameEnd) {
            return
        }
        needNewQuestion = false
        gameStarted = true
        level += 1
        _numberOfQuestion.intValue = level
        _questionMoney.intValue = createQuestionMoney(level)
        _timer.intValue = 30
        if (_questions.indices.contains(level)) {
            _question.value = _questions[level].question
            _answers.value = createAnswers(_questions[level])
        }
        soundControl(R.raw.clock_sound)
        startTimer()

    }

    private fun resumeGame() {
        if (gameEnd) {
            return
        }
        if (gameAnswered) {
            handleAnswer()
        }
        Log.d(TAG, "resumeGame")
        startTimer()
        soundControl(needResume = true)
    }

    private fun pauseGame() {
        Log.d(TAG, "pauseGame")
        stopTimer()
    }

    private fun stopTimer() {
        jobTimer.cancel()
    }

    private fun startTimer() {
        if (gameEnd || gameAnswered) {
            return
        }
        Log.d(TAG, "startTimer")
        jobTimer.cancel()
        jobTimer = viewModelScope.launch {
            while (timer.value > 0) {
                delay(1000)
                _timer.intValue = _timer.intValue - 1
            }
            if (timer.value == 0) {
                userSum = calculateSum(false)
                when {
                    userSum >= 1000 -> {
                        resetFlags()
                        navigation(true)
                    }

                    else -> {
                        resetFlags()
                        soundControl(needPause = true)
                        navigation(true)
                    }
                }
            }
        }
    }

    private fun handleAnswer() {
        viewModelScope.launch {
            while (delayBeforeShowRightAnswer > 0) {
                delayBeforeShowRightAnswer -= 1
                delay(1000)
            }
            soundControl(needPause = true)
            if (!_questions.indices.contains(level)) {
                return@launch
            }
            val question = _questions[level]
            _answers.value = _answers.value.map {
                it.copy(
                    isRightAnswer = question.correctAnswer == it.title,
                    isError = it.isSelected && it.title != question.correctAnswer
                )
            }.toImmutableList()
            delay(500)
            navigation(isRightAnswer.not())
            resetFlags()
        }
    }

    private fun navigation(isFinish: Boolean = false) {
        needNewQuestion = isFinish.not()
        viewModelScope.launch {
            if (isFinish) {
                when {
                    userSum >= 1000 -> {
                        _navigationFlow.emit(
                            NavigationFromGameState.NavigationToResultScreen(
                                isFinish = true,
                                level = level,
                                countMoney = userSum
                            )
                        )
                    }

                    else -> {
                        _navigationFlow.emit(
                            NavigationFromGameState.NavigationToFinishScreen(
                                level = level,
                                countMoney = userSum
                            )
                        )
                    }
                }
            } else {
                _navigationFlow.emit(
                    NavigationFromGameState.NavigationToResultScreen(
                        isFinish = level == 15,
                        level = level,
                        countMoney = userSum
                    )
                )
            }
        }
        if (isFinish || level == 15) {
            resetQuestions()
        }
    }

    private fun resetFlags() {
        gameEnd = false
        gameStarted = false
        gamePause = false
        gameAnswered = false
        isRightAnswer = false
        delayBeforeShowRightAnswer = 5
        userAnswer = ""
    }

    private fun soundControl(
        soundResId: Int? = null,
        needPause: Boolean = false,
        needResume: Boolean = false
    ) {
        when {
            needPause -> {
                mediaPlayer.pause()
            }

            needResume -> {
                mediaPlayer.start()
            }

            soundResId != null -> {
                mediaPlayer.reset()
                mediaPlayer = MediaPlayer.create(application, soundResId)
                mediaPlayer.start()
            }
        }
    }

    private fun createAnswers(question: Question): ImmutableList<AnswerItem> {
        val resultList = mutableListOf<AnswerItem>()
        resultList.addAll(question.incorrectAnswers.map { AnswerItem(title = it) })
        resultList.add(AnswerItem(title = question.correctAnswer))
        return resultList.shuffled().toImmutableList()
    }

    private fun createQuestionMoney(level: Int): Int {
        return when (level) {
            1 -> {
                100
            }

            2 -> {
                200
            }

            3 -> {
                300
            }

            4 -> {
                500
            }

            5 -> {
                1000
            }

            6 -> {
                2000
            }

            7 -> {
                4000
            }

            8 -> {
                8000
            }

            9 -> {
                16000
            }

            10 -> {
                32000
            }

            11 -> {
                64000
            }

            12 -> {
                125000
            }

            13 -> {
                250000
            }

            14 -> {
                500000
            }

            else -> {
                1000000
            }

        }
    }

    private fun calculateSum(isRightAnswer: Boolean): Int {
        return if (isRightAnswer) {
            when (userSum) {
                0 -> {
                    100
                }

                100 -> {
                    200
                }

                200 -> {
                    300
                }

                300 -> {
                    500
                }

                500 -> {
                    1000
                }

                1000 -> {
                    2000
                }

                2000 -> {
                    4000
                }

                4000 -> {
                    8000
                }

                8000 -> {
                    16000
                }

                16000 -> {
                    32000
                }

                32000 -> {
                    64000
                }

                64000 -> {
                    125000
                }

                125000 -> {
                    250000
                }

                250000 -> {
                    500000
                }

                else -> {
                    1000000
                }
            }
        } else {
            when {
                userSum >= 32000 -> {
                    32000
                }

                userSum >= 1000 -> {
                    1000
                }

                else -> {
                    0
                }
            }
        }
    }
}