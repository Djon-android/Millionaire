package com.example.millionaire.presentation.records_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class Record(
    val id: Int,
    val name: String,
    val score: Int
)

class RecordViewModel() : ViewModel() {
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