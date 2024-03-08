package com.example.millionaire.presentation.login_screen

import android.util.Log
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen (navigateToGame: () -> Unit) {
    val context = LocalContext.current
    val buttonBlueGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF025D83), Color(0xFF022B54),
            Color(0xFF020631), Color(0xFF083C66)
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), contentDescription = "logo",
            modifier = Modifier.size(250.dp)
        )

        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "Введите свой никнейм",
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        var filledName by remember { mutableStateOf("") }
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
// заменил label на placeholder
            placeholder = {
                Text(
                    text = "Enter your nickname",
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally),
                )
                    },
            value = filledName,
            onValueChange = {
                filledName = it
                            },
            textStyle = TextStyle(
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier
                .height(58.dp)
                .width(328.dp)
                .padding(horizontal = 16.dp)
                .border(2.dp, Color.White, CutCornerShape(50)),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            ),

            shape = CutCornerShape(50),
            singleLine = true
        )
        Spacer(modifier = Modifier.padding(20.dp))
        Button(
            modifier = Modifier
                .size(311.dp, 62.dp)
                .background(
                    brush = buttonBlueGradient,
                    shape = CutCornerShape(50.dp)
                ),
            onClick = {
                if(filledName.isNotEmpty()){
                              navigateToGame()

                }
                else{
                    val toast = Toast.makeText(context,
                        "Введите никнейм",
                        Toast.LENGTH_SHORT).show()
                }

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, contentColor = Color.White
            ),
            shape = CutCornerShape(50.dp),
            border = BorderStroke(2.dp, Color.White)
        ) {
            Text(text = "Play", fontSize = 24.sp)
        }
    }
}




@Preview (showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview(){
    LoginScreen({})
}