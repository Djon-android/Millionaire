package com.example.covertervk.data.remote.dto

import com.example.covertervk.domain.model.Value

data class ValueDto(
    val base_code: String,
    val conversion_rate: Double,
    val conversion_result: Double,
    val documentation: String,
    val result: String,
    val target_code: String,
    val terms_of_use: String,
    val time_last_update_unix: Int,
    val time_last_update_utc: String,
    val time_next_update_unix: Int,
    val time_next_update_utc: String
)

fun ValueDto.toValue(): Value {
    return Value(
        base_code = base_code,
        conversion_rate = conversion_rate,
        conversion_result = conversion_result,
        result = result,
        target_code = target_code
    )
}