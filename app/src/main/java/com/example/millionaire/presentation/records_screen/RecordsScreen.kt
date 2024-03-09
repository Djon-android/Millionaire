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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.millionaire.R
import com.example.millionaire.presentation.common_components.CutButton
import com.example.millionaire.presentation.ui.theme.BlueGradient
import com.example.millionaire.presentation.ui.theme.Typography
import com.example.millionaire.presentation.ui.theme.White

@Composable
fun RecordsScreen(
    viewModel: RecordViewModel = hiltViewModel(),
    navigateToMainScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(R.drawable.logo),
                modifier = Modifier
                    .size(125.dp)
                    .shadow(12.dp, shape = RoundedCornerShape(90.dp)),
                contentDescription = stringResource(id = R.string.logo)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.score),
                style = Typography.displayLarge
            )
            if (viewModel.records.value.isEmpty()) {
                Text(
                    text = stringResource(R.string.empty_records),
                    color = Color.Gray,
                    style = Typography.titleLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    items(viewModel.records.value) { record ->
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .border(
                                    border = BorderStroke(2.dp, Color.White),
                                    shape = RoundedCornerShape(20)
                                )
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = record.username,
                                style = Typography.titleLarge.copy(
                                    color = White
                                )
                            )
                            Text(
                                text = record.sum.toString(),
                                style = Typography.titleLarge.copy(
                                    color = White
                                )
                            )
                        }
                    }
                }
            }
        }
        CutButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .background(Brush.verticalGradient(BlueGradient), CutCornerShape(50)),
            onClick = navigateToMainScreen
        ) {
            Text(text = stringResource(R.string.mainscreen))
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

