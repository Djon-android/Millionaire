package com.example.millionaire.presentation.result_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.millionaire.R

const val level = 5
const val isFinishGame = false

@Preview(showBackground = true)
@Composable
fun ResultScreen() {

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



    if (isFinishGame != true) { // Игра продолжается
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds
                )
                .statusBarsPadding()
                .safeContentPadding()
        ) {
            Row {

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
                        Spacer(modifier = Modifier.height(4.dp))
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
                                            brush = buttonBlueGradient,
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
                                            brush = buttonBlueGradient,
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
                                            brush = buttonYellowGradient,
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
                                            brush = buttonBlueGradient,
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


                        }
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Логотип игры",
                    modifier = Modifier
                        .size(160.dp)
                        .align(Alignment.TopCenter)
                        .padding(top = 60.dp)
                )
            }

        }
    } else {

        // Игра закончилась, делаем что-то еще
    }
}

