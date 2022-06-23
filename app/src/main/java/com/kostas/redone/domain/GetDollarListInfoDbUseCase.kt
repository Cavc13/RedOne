package com.kostas.redone.domain

import javax.inject.Inject

class GetDollarListInfoDbUseCase @Inject constructor(
    private val repository: DollarRepository
){
    suspend operator fun invoke() = repository.getDollarListInfoDb()
}