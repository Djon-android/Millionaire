package com.example.millionaire.data.network.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QuestionDto(
    @SerializedName("type")
    @Expose
    val type: String? = null,

    @SerializedName("difficulty")
    @Expose
    val difficulty: String? = null,

    @SerializedName("category")
    @Expose
    val category: String? = null,

    @SerializedName("question")
    @Expose
    val question: String? = null,

    @SerializedName("correct_answer")
    @Expose
    val correctAnswer: String? = null,

    @SerializedName("incorrect_answers")
    @Expose
    val incorrectAnswers: List<String>? = null
)