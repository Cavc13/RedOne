package com.kostas.redone.domain

import javax.inject.Inject

class StartDollarWorkerUseCase @Inject constructor(
    private val repository: DollarRepository
){
    suspend operator fun invoke(checkPoint: Double) = repository.startDollarWorker(checkPoint)
}