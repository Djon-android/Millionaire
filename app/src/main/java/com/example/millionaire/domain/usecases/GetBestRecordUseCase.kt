package com.example.millionaire.domain.usecases

import com.example.millionaire.domain.repository.RecordsRepository
import javax.inject.Inject

class GetBestRecordUseCase @Inject constructor(
    private val repository: RecordsRepository
) {
    operator fun invoke() = repository.getBestRecord()
}