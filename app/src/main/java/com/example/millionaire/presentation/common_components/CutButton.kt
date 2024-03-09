package com.example.millionaire.presentation.common_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CutButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent
    ),
    border: BorderStroke = BorderStroke(3.dp, Color.White),
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier,
        border = border,
        shape = CutCornerShape(50),
        colors = colors,
        onClick = onClick,
        enabled = enabled
    ) {
        content()
    }
}