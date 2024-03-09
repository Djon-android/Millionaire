package com.example.millionaire.presentation.records_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.millionaire.R
import com.example.millionaire.utils.Button

private val _score: String = "Score"
private val _mainScreen: String = "Main screen"
private val _emtyRecordsList: String = "List of records is empty..."

@Composable
fun RecordsScreen(
    listRecords: RecordViewModel = hiltViewModel(),
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

