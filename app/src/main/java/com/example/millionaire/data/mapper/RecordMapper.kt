package com.example.millionaire.data.mapper

import com.example.millionaire.data.local.entity.PlayerResultEntity
import com.example.millionaire.domain.model.PlayerResult

fun PlayerResultEntity.toPlayerResult() = PlayerResult(
    id = id,
    username = userName,
    sum = result
)