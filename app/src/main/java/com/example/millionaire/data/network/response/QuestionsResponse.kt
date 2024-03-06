package com.example.millionaire.data.network.response

import com.example.millionaire.data.network.dto.QuestionDto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QuestionsResponse(

    @SerializedName("results")
    @Expose
    val responseList: List<QuestionDto>? = null
)