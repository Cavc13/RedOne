package com.kostas.redone.domain

import javax.inject.Inject

class GetMonthDollarPriceUseCase @Inject constructor(
    private val repository: DollarRepository
){
    suspend operator fun invoke() = repository.getMonthDollarPriceApi()
}