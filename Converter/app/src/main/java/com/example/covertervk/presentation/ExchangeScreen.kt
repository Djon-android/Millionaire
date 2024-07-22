package com.example.covertervk.presentation

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.ui.text.font.FontWeight
import com.example.covertervk.domain.use_case.getExchangeUseCase

@Composable
fun ExchangeScreen(viewModel: ExchangeViewModel) {
    val state = viewModel.state.value

    var fromValue by remember { mutableStateOf("") }
    var toValue by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    var fromCurrencyExpanded by remember { mutableStateOf(false) }
    var toCurrencyExpanded by remember { mutableStateOf(false) }

    val currencies = listOf("USD", "EUR", "GBP")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }
            state.coin != null -> {
                Text(text = "Converted Amount: ${state.coin.conversion_result}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                    )
            }
            state.error.isNotEmpty() -> {
                viewModel.state.value.coin?.let { Text(text = "Не верно веденные данные", color = Color.Red) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
            ) {
                Button(
                    onClick = {fromCurrencyExpanded = true},
                    modifier = Modifier.align(Alignment.CenterStart)
                        .width(100.dp)
                    ){
                    Text(text = fromValue)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(
                    expanded = fromCurrencyExpanded,
                    onDismissRequest = { fromCurrencyExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    currencies.forEach { currency ->
                        DropdownMenuItem(
                            text = { Text(text = currency) },
                            onClick = {
                                fromValue = currency
                                fromCurrencyExpanded = false
                            })
                    }
                }
            }
            Box(
            ) {
                Button(
                    onClick = {toCurrencyExpanded = true},
                    modifier = Modifier
                        .width(100.dp)
                ) {
                    Text(text = toValue)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(
                    expanded = toCurrencyExpanded,
                    onDismissRequest = { toCurrencyExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    currencies.forEach { currency ->
                        DropdownMenuItem(
                            onClick = {
                                toValue = currency
                                toCurrencyExpanded = false
                            },
                            text = { Text(text = currency) }
                        )

                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {viewModel.getExchange(fromValue, toValue, amount) }) {
            Text("Get Exchange Rate")
        }
        }
    }

