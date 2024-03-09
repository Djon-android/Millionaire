package com.example.millionaire.domain.usecases

import com.example.millionaire.domain.repository.RecordsRepository
import javax.inject.Inject

class SaveResultUseCase @Inject constructor(
    private val repository: RecordsRepository
) {
    suspend operator fun invoke(sum: Int) = repository.saveResult(sum)
}