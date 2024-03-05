package com.example.millionaire.presentation.finish_game_screen

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
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
    val lifecycle = LocalLifecycleOwner.current.lifecycle

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
                modifier = Modifier.size(width = 195.dp, height = 195.dp),
                contentDescription = "logo"
            )
            Text(
                text = _gameOver,
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight(600)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = _levels + "$level",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight(400)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.coin),
                    modifier = Modifier.size(width = 32.dp, height = 32.dp),
                    contentDescription = "coin"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "$$countMoney",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight(600)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                    navigateToLoginScreen()
                }
            }) {
                Text(
                    text = _newGame,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight(600)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                    navigateToMainScreen()
                }
            }) {
                Text(
                    text = _mainScreen,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight(600)
                )
            }

            Spacer(modifier = Modifier.height(64.dp))

        }
    }
}

