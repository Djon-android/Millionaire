package com.example.millionaire.presentation.main_screen

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.millionaire.R
import kotlinx.coroutines.launch


@ExperimentalMaterial3Api
@Composable
fun MainScreen(

    navigationToLogin: () -> Unit,
    navigationToResults: () -> Unit,
    isSecondStart: Boolean = false,
    bestScore :Int? = null
) {
    val continueGame by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    var bottomSheetText by remember { mutableStateOf("") }
    
    val buttonYellowGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE1CF30), Color(0xFFE19A30),
            Color(0xFFE19A30), Color(0xFFE1CF30)
        )
    )
    val buttonBlueGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF025D83), Color(0xFF022B54),
            Color(0xFF020631), Color(0xFF083C66)
        )
    )
    BottomSheetScaffold(
        sheetContent = {
            Text(text = bottomSheetText, modifier = Modifier
                .fillMaxSize(0.8f)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 50.dp, start = 15.dp, end = 15.dp), fontSize = 19.sp)

        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContentColor = Color.White,
        sheetContainerColor = Color(0xFF2c2e47)
    ) {

        Image(painter = painterResource(id = R.drawable.background),
            contentDescription = "Задний фон"
        , modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Логотип игры",
                modifier = Modifier
                    .size(195.dp)
                    .shadow(elevation = 20.dp, shape = CircleShape)
            )
            Text(
                text = "Who Wants\nto be a Millionare",
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp
            )

            if(isSecondStart && bestScore!= null) {
                Text(
                    text = "All-time best score",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.alpha(0.5f)
                )
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.coin),
                        contentDescription = "Монетка",
                        modifier = Modifier.size(24.dp).padding(end = 8.dp)
                    )
                    Text(text = "$${bestScore.toString()}",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)

                }
            }
            Spacer(modifier = Modifier.size(120.dp))
            Button(
                modifier = Modifier
                    .size(311.dp, 62.dp)
                    .background(
                        brush = buttonYellowGradient,
                        shape = CutCornerShape(50.dp)
                    ),
                onClick = {
                    navigationToLogin()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, contentColor = Color.White
                ),
                shape = CutCornerShape(50.dp),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Text(text = "New game", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.size(20.dp))
            Button(
                modifier = Modifier
                    .size(311.dp, 62.dp)
                    .background(
                        brush = buttonBlueGradient,
                        shape = CutCornerShape(50.dp)
                    ),
                onClick = {
                    navigationToLogin()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, contentColor = Color.White
                ),
                shape = CutCornerShape(50.dp),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Text(text = "Records", fontSize = 24.sp)
            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                Text(
                    text = "© TEAM 2",
                    modifier = Modifier
                        .padding(20.dp)
                        .clickable {
                            bottomSheetText = textTeam
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        },
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.size(60.dp))
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd,
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Правила игры",
                modifier = Modifier
                    .padding(45.dp)
                    .size(27.dp)
                    .clickable {
                        bottomSheetText = textRules
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                tint = Color.White
            )
        }
    }
}

@Preview
@ExperimentalMaterial3Api
@Composable
fun MainScreenPreview() {
    MainScreen({}, {},true,15000)
}

val textTeam =
    "Евгений - evgeny_mobile\n\nБулат - mezeksan\n\nШамси - umaq\n\nНикита - nikita_novikov2308"
val textRules =
    "Игра \"Кто хочет стать миллионером\" - это популярная телевизионная игра, основанная на британском формате \"Who Wants to Be a Millionaire?\". В игре участвует один участник, который поочередно отвечает на вопросы и пытается выиграть максимальный денежный приз.\n" +
            "\n" +
            "Вот основные правила игры:\n" +
            "\n" +
            "Участнику предлагается серия вопросов разной сложности, на каждый из которых предоставляется четыре варианта ответа.\n" +
            "\n" +
            "Участник выбирает один из вариантов (или может не выбирать, если ему неизвестен верный ответ) и затем подтверждает свой выбор.\n" +
            "\n" +
            "При правильном ответе участник продвигается дальше по игровому дереву, при неправильном ответе он выбывает из игры.\n" +
            "\n" +
            "Чем дальше участник продвигается, тем сложнее и ценнее становятся вопросы и призы.\n" +
            "\n" +
            "Участник имеет возможность использовать подсказки, такие как \"50/50\" (удаление двух неверных вариантов ответа), \"Звонок другу\" (звонок знакомому для получения совета) и \"Помощь зала\" (опрос аудитории).\n" +
            "\n" +
            "Цель участника - дойти до последнего вопроса и выиграть максимальный денежный приз, который обычно является суммой в размере миллиона или другой крупной суммы денег"