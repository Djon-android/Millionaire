package com.example.millionaire.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle

@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
) {
    var colorGradient by remember { mutableStateOf(blueGradient) }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    androidx.compose.material3.Button(
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