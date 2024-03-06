package com.example.millionaire.presentation.finish_game_screen

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import com.example.millionaire.R

private val _gameOver: String = "Game over!"
private val _newGame: String = "New game"
private val _mainScreen: String = "Main screen"
private val _levels: String = "Level "


@Composable
fun FinishGameScreen(
    level: Int,
    countMoney: Int,
    navigateToLoginScreen: () -> Unit,
    navigateToMainScreen: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
    )
    {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                modifier = Modifier
                    .size(width = 195.dp, height = 195.dp)
                    .shadow(12.dp, shape = RoundedCornerShape(90.dp)),
                contentDescription = "logo"
            )
            Text(
                text = _gameOver,
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight(600)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = _levels + "$level",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight(400)
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.coin),
                    modifier = Modifier
                        .size(width = 42.dp, height = 42.dp)
                        .shadow(8.dp, shape = RoundedCornerShape(90.dp)),
                    contentDescription = "coin"
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "$$countMoney",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight(600)
                )
            }
            Spacer(modifier = Modifier.height(160.dp))
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(text = _newGame, onClick = { navigateToLoginScreen() })
            Spacer(modifier = Modifier.height(12.dp))
            Button(text = _mainScreen, onClick = { navigateToMainScreen() })
            Spacer(modifier = Modifier.height(62.dp))
        }
    }
}

@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
) {
    var colorGradient by remember { mutableStateOf(blueGradient) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    Button(
        modifier = Modifier
            .height(62.dp)
            .width(311.dp)
            .background(
                brush = colorGradient, shape = CutCornerShape(50)
            ),
        border = BorderStroke(3.dp, Color.White),
        shape = CutCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = {
            colorGradient = orangeGradient
            if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                onClick()
            }
        }) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight(600)
        )
    }
}

val blueGradient: Brush = Brush.verticalGradient(
    colors = listOf(
        Color(8, 60, 102),
        Color(2, 43, 84),
        Color(2, 6, 49),
        Color(8, 60, 102)
    )
)

val orangeGradient: Brush = Brush.verticalGradient(
    colors = listOf(
        Color(225, 207, 48),
        Color(225, 154, 48),
        Color(225, 154, 48),
        Color(225, 207, 48)
    )
)
