package com.example.millionaire.presentation.records_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.millionaire.domain.usecases.GetRecordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Record(
    val id: Int,
    val name: String,
    val score: Int
)

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val getRecordsUseCase: GetRecordsUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            val records = getRecordsUseCase()
            Log.d("!!!!!", "$records")
        }
    }
    private val _listRecords =
        mutableStateOf(
            listOf<Record>(
//                Record(1, "User1", 11111111),
//                Record(2, "User2", 1111111),
//                Record(3, "User3", 111111),
//                Record(4, "User4", 11111),
//                Record(5, "User5", 1111),
//                Record(6, "User6", 11111111),
//                Record(7, "User7", 1111111),
//                Record(8, "User8", 111111),
//                Record(9, "User9", 11111),
//                Record(10, "User10", 1111),
//                Record(11, "User11", 11111111),
//                Record(12, "User12", 1111111),
//                Record(13, "User13", 111111),
//                Record(14, "User14", 11111),
//                Record(15, "User15", 1111)

            )
        )

    fun getListRecords() = _listRecords.component1().sortedByDescending { record -> record.score }
}