package com.example.millionaire.presentation.login_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.millionaire.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

//@Composable
//fun LoginScreen(
//    navigateToGame: () -> Unit
//) {
//}


@Composable
fun LoginScreen (){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
    )
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.logo), contentDescription = "logo",
            modifier = Modifier.scale(1f)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        var filledName by remember {mutableStateOf("")}
        OutlinedTextField(
// Не получилось убрать label при фокусе, иначе сдвигает вводимый текст за границу.
//            label = {
//                Text(
//                    text = "Enter your name",
//                    textAlign = TextAlign.Center,
//                    color = Color.Gray,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .align(Alignment.CenterHorizontally),
//                )
//                    },
            value = filledName,
            onValueChange = {
                filledName = it
                            },
            textStyle = TextStyle(
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .height(58.dp)
                .width(328.dp)
                .padding(horizontal = 16.dp)
                .border(3.dp, Color.White, CutCornerShape(50)),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            ),

            shape = CutCornerShape(50),
            singleLine = true
        )
    }
}

@Preview (showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview(){
    LoginScreen()
}