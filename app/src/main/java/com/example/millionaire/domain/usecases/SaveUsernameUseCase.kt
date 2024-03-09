package com.example.millionaire.domain.usecases

import com.example.millionaire.domain.repository.RecordsRepository
import javax.inject.Inject

class SaveUsernameUseCase @Inject constructor(
    private val repository: RecordsRepository
) {
    suspend operator fun invoke(username: String) = repository.saveUsername(username)
}