package com.example.millionaire.presentation.finish_game_screen

import android.media.MediaPlayer
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.millionaire.R
import com.example.millionaire.presentation.common_components.CutButton
import com.example.millionaire.presentation.ui.theme.BlueGradient
import com.example.millionaire.presentation.ui.theme.OrangeGradient
import com.example.millionaire.presentation.ui.theme.Typography
import com.example.millionaire.presentation.ui.theme.White

@Composable
fun FinishGameScreen(
    level: Int,
    countMoney: Int,
    isLosing: Boolean,
    navigateToLoginScreen: () -> Unit,
    navigateToMainScreen: () -> Unit
) {

    BackHandler {

    }

    PlayMusicOnEntry(isLosing = isLosing)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.logo),
                modifier = Modifier
                    .size(195.dp)
                    .shadow(12.dp, shape = RoundedCornerShape(90.dp)),
                contentDescription = stringResource(id = R.string.logo)
            )
            Text(
                text = stringResource(R.string.game_over),
                style = Typography.displayLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${stringResource(R.string.level)} $level",
                style = Typography.titleMedium.copy(
                    color = White.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.money),
                    modifier = Modifier
                        .size(42.dp),
                    contentDescription = stringResource(R.string.coin)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "$$countMoney",
                    style = Typography.labelLarge
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CutButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp)
                        .padding(horizontal = 32.dp)
                        .background(Brush.verticalGradient(OrangeGradient), CutCornerShape(50)),
                    onClick = navigateToLoginScreen
                ) {
                    Text(
                        text = stringResource(R.string.new_game),
                        style = Typography.labelLarge
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                CutButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp)
                        .padding(horizontal = 32.dp)
                        .background(Brush.verticalGradient(BlueGradient), CutCornerShape(50)),
                    onClick = navigateToMainScreen
                ) {
                    Text(
                        text = stringResource(R.string.mainscreen),
                        style = Typography.labelLarge
                    )
                }
                Spacer(modifier = Modifier.height(62.dp))
            }
        }
    }
}

@Composable
fun PlayMusicOnEntry(isLosing: Boolean) {
    val context = LocalContext.current
    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.soundoflose)
    }

    LaunchedEffect(Unit) {
        if (isLosing) {
            mediaPlayer.start()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}
