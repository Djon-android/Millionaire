package com.example.millionaire.data.mapper

import com.example.millionaire.data.network.dto.QuestionDto
import com.example.millionaire.domain.model.Question

fun QuestionDto.toQuestion() = Question(
    type = type ?: "",
    difficulty = difficulty ?: "",
    category = category ?: "",
    question = question ?: "",
    correctAnswer = correctAnswer ?: "",
    incorrectAnswers = incorrectAnswers ?: listOf()
)