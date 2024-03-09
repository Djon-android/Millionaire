package com.example.millionaire.presentation.result_screen


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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.millionaire.R
import android.media.MediaPlayer
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun PlayMusicOnEntry(isFinishGame: Boolean) {
    val context = LocalContext.current
    val mediaPlayer =
        when(isFinishGame){
            true -> remember { MediaPlayer.create(context, R.raw.soundoflose) }
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
fun ResultScreen(
    isFinishGame: Boolean,
    level: Int,
    countMoney: Int,
    navigationToFinishGame: (Int, Int) -> Unit,
    navigationToBack: () -> Unit
) {
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
    val buttonLightBlueGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF60C3FB), // #60C3FB
            Color(0xFF1180BF), // #1180BF
            Color(0xFF1D88C5), // #1D88C5
            Color(0xFF26A6EE)
        )
    )
    val GreenGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF3B8E14), // #3B8E14
            Color(0xFF266608), // #266608
            Color(0xFF266608), // #266608
            Color(0xFF3D881A)  // #3D881A
        )
    )
    val sums = listOf<String>(
        "500", "1,000", "2,000", "3,000", "5,000", "7.500", "10,000", "12,500",
        "15,000", "25,000", "50,000", "100,000", "250,000", "500,000", "1,000,000"
    )


    // Условие совпадения уровня и переменной level
    val levelMatch = { index: Int -> level == index + 1 }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
            .statusBarsPadding()
            .safeContentPadding()
            .clickable {navigationToBack() }
    ) {
        Row {
            if (!isFinishGame) {
                PlayMusicOnEntry(isFinishGame)
                Button(
                    onClick = { navigationToFinishGame(level, countMoney)
                        },
                    modifier = Modifier.padding(top = 25.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.lose),
                        contentDescription = "Логотип игры",
                        modifier = Modifier
                            .size(32.dp)
                    )
                }
            } else {
                PlayMusicOnEntry(isFinishGame)
                Row (modifier = Modifier.fillMaxWidth().size(75.dp)) {
                    Box (modifier = Modifier.fillMaxWidth().size(32.dp).padding(top = 55.dp)){

                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp), reverseLayout = true
            ) {
                items(15) { index ->
                    val sum = sums[index]
                    Spacer(modifier = Modifier.height(4.dp))
                    val cardColor = when {
                        index + 1 == level -> GreenGradient
                        (index == 4) || (index == 9) -> buttonLightBlueGradient
                        index == 14 -> buttonYellowGradient
                        else -> buttonBlueGradient
                    }
                    if (index == 4) {
                        Card(
                            modifier = Modifier
                                .size(311.dp, 36.dp),
                            shape = CutCornerShape(50.dp),
                            border = BorderStroke(2.dp, Color.White),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = cardColor,
                                        shape = CutCornerShape(50.dp)
                                    ),
                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {
                                Text(
                                    modifier = Modifier.padding(top = 5.dp, start = 25.dp),
                                    text = "${index + 1}:",
                                    style = TextStyle(fontSize = 18.sp),
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier.padding(top = 5.dp, end = 30.dp),
                                    text = "$ 5,000",
                                    style = TextStyle(fontSize = 18.sp),
                                    color = Color.White
                                )
                            }
                        }
                    } else if (index == 9) {
                        Card(
                            modifier = Modifier
                                .size(311.dp, 36.dp),
                            shape = CutCornerShape(50.dp),
                            border = BorderStroke(2.dp, Color.White),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = cardColor,
                                        shape = CutCornerShape(50.dp)
                                    ),
                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {
                                Text(
                                    modifier = Modifier.padding(top = 5.dp, start = 25.dp),
                                    text = "${index + 1}:",
                                    style = TextStyle(fontSize = 18.sp),
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier.padding(top = 5.dp, end = 30.dp),
                                    text = "$ 25,000",
                                    style = TextStyle(fontSize = 18.sp),
                                    color = Color.White
                                )
                            }
                        }
                    } else if (index == 14) {
                        Card(
                            modifier = Modifier
                                .size(311.dp, 36.dp),
                            shape = CutCornerShape(50.dp),
                            border = BorderStroke(2.dp, Color.White),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = cardColor,
                                        shape = CutCornerShape(50.dp)
                                    ),
                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {
                                Text(
                                    modifier = Modifier.padding(top = 5.dp, start = 25.dp),
                                    text = "${index + 1}:",
                                    style = TextStyle(fontSize = 18.sp),
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier.padding(top = 5.dp, end = 30.dp),
                                    text = "$ 1,000,000",
                                    style = TextStyle(fontSize = 18.sp),
                                    color = Color.White
                                )
                            }
                        }
                    } else {
                        Card(
                            modifier = Modifier
                                .size(311.dp, 36.dp),
                            shape = CutCornerShape(50.dp),
                            border = BorderStroke(2.dp, Color.White),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = cardColor,
                                        shape = CutCornerShape(50.dp)
                                    ),
                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {
                                Text(
                                    modifier = Modifier.padding(top = 5.dp, start = 25.dp),
                                    text = "${index + 1}:",
                                    style = TextStyle(fontSize = 18.sp),
                                    color = Color.White
                                )
                                Text(
                                    modifier = Modifier.padding(top = 5.dp, end = 30.dp),
                                    text = "$ $sum",
                                    style = TextStyle(fontSize = 18.sp),
                                    color = Color.White
                                )
                            }
                        }


                    }
                }
            }
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Логотип игры",
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.TopCenter)
                    .padding(top = 1.dp)
            )
        }

    }

}

