package com.example.millionaire.presentation.login_screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.example.millionaire.R
import com.example.millionaire.presentation.common_components.CutButton
import com.example.millionaire.presentation.ui.theme.BlueGradient
import com.example.millionaire.presentation.ui.theme.Typography
import com.example.millionaire.presentation.ui.theme.White

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToGame: () -> Unit
) {
    val context = LocalContext.current
    val error = stringResource(id = R.string.input_your_username)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.logo),
                modifier = Modifier
                    .size(250.dp)
                    .shadow(40.dp, CircleShape)
            )

            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = stringResource(R.string.input_your_username),
                style = Typography.displayMedium
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(58.dp)
                    .border(BorderStroke(3.dp, White), shape = CutCornerShape(50))
                    .background(Color.Transparent, shape = CutCornerShape(50))
                    .padding(horizontal = 32.dp, vertical = 10.dp)
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = viewModel.username.value,
                    onValueChange = viewModel::changeUsername,
                    textStyle = Typography.labelLarge.copy(
                        color = White
                    ),
                    singleLine = true,
                    cursorBrush = Brush.verticalGradient(listOf(White, White))
                )
                if (viewModel.username.value.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.input_your_username),
                        color = White.copy(alpha = 0.5f)
                    )
                }
            }
            Spacer(modifier = Modifier.padding(20.dp))
            CutButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(62.dp)
                    .background(
                        brush = Brush.verticalGradient(BlueGradient),
                        shape = CutCornerShape(50.dp)
                    ),
                onClick = {
                    if (viewModel.username.value.isNotBlank() &&
                        lifecycle.currentState == Lifecycle.State.RESUMED
                    ) {
                        viewModel.saveUsername()
                        navigateToGame()
                    } else {
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.play),
                    style = Typography.bodyLarge,
                    color = White
                )
            }
        }
    }
}