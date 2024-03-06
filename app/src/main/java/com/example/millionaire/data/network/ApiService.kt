package com.example.millionaire.data.network

import com.example.millionaire.data.network.response.QuestionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String
    ): QuestionsResponse
}