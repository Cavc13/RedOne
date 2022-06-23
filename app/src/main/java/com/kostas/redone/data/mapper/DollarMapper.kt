package com.kostas.redone.data.mapper

import com.kostas.redone.data.database.DollarInfoDbModel
import com.kostas.redone.data.network.dto.DollarDto
import com.kostas.redone.data.resource.reverseDate
import com.kostas.redone.domain.model.Dollar
import javax.inject.Inject

class DollarMapper @Inject constructor(){
    fun mapDollarDtoToDollarInfoDb(dollarDto: DollarDto): DollarInfoDbModel {
        with(dollarDto) {
            return DollarInfoDbModel(
                id = UNDEFINED_ID,
                date = reverseDate(date),
                currency = id ?: "",
                value = value ?: "",
                nominal = nominal ?: NOMINAL_ONE
            )
        }
    }

    fun mapDollarInfoDbToDollar(dollarInfoDbModel: DollarInfoDbModel): Dollar {
        with(dollarInfoDbModel) {
            return Dollar(
                date = reverseDate(date),
                id = currency,
                value = value,
                nominal = nominal
            )
        }
    }

    companion object {
        const val UNDEFINED_ID = 0L
        const val NOMINAL_ONE = 1
    }
}