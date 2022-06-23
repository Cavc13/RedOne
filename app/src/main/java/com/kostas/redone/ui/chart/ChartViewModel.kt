package com.kostas.redone.ui.chart

import androidx.lifecycle.ViewModel
import com.kostas.redone.domain.GetDollarListInfoDbUseCase
import javax.inject.Inject

class ChartViewModel @Inject constructor(
    private val getDollarListInfoDbUseCase: GetDollarListInfoDbUseCase
): ViewModel() {

}