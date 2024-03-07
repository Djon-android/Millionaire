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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.millionaire.R
import com.example.millionaire.utils.Button

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
