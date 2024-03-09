package com.example.millionaire.data.mapper

import android.text.Html
import androidx.core.text.HtmlCompat
import com.example.millionaire.data.network.dto.QuestionDto
import com.example.millionaire.domain.model.Question

fun QuestionDto.toQuestion() = Question(
    type = type ?: "",
    difficulty = difficulty ?: "",
    category = category ?: "",
    question = Html.fromHtml(question ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
    correctAnswer = Html.fromHtml(correctAnswer ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
    incorrectAnswers = incorrectAnswers?.map {
        Html.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    } ?: listOf()
)