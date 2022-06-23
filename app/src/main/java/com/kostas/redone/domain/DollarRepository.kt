package com.kostas.redone.domain

import com.kostas.redone.domain.model.Dollar

interface DollarRepository {

    suspend fun getMonthDollarPriceApi(): List<Dollar>

    suspend fun getDollarListInfoDb(): List<Dollar>

    suspend fun startDollarWorker(checkPoint: Double)
}