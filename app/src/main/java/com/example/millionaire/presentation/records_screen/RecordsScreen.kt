package com.example.millionaire.presentation.records_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

private val _score: String = "Score"
private val _mainScreen: String = "Main screen"
private val _emtyRecordsList: String = "List of records is empty..."

@Composable
fun RecordsScreenPreview(
    listRecords: RecordViewModel,
    navigateToMainScreen: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.background),
                contentScale = ContentScale.FillBounds
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    )
    {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(R.drawable.logo),
            modifier = Modifier
                .size(width = 125.dp, height = 125.dp)
                .shadow(12.dp, shape = RoundedCornerShape(90.dp)),
            contentDescription = "logo"
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = _score,
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight(600)
        )
        if (listRecords.getListRecords().isEmpty()) {
            Text(
                text = _emtyRecordsList,
                color = Color.Gray,
                fontSize = 18.sp,
                fontWeight = FontWeight(600),

                )
            Spacer(modifier = Modifier.height(300.dp))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(0.85f)
                    .padding(16.dp)
            ) {
                items(listRecords.getListRecords()) { scope: Record ->
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .border(
                                border = BorderStroke(2.dp, Color.White),
                                shape = RoundedCornerShape(20)
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = scope.name, modifier = Modifier.padding(8.dp))
                        Text(text = scope.score.toString(), modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(text = _mainScreen, onClick = { navigateToMainScreen() })
            Spacer(modifier = Modifier.height(24.dp))
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
