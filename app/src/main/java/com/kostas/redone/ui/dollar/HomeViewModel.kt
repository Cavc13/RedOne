package com.kostas.redone.ui.dollar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kostas.redone.domain.GetMonthDollarPriceUseCase
import com.kostas.redone.domain.StartDollarWorkerUseCase
import com.kostas.redone.domain.model.Dollar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getMonthDollarPriceUseCase: GetMonthDollarPriceUseCase,
    private val startDollarWorkerUseCase: StartDollarWorkerUseCase
) : ViewModel() {

    private val _dollarFlow = MutableStateFlow<List<Dollar>>(emptyList())
    val dollarFlow: StateFlow<List<Dollar>>
        get() = _dollarFlow


    fun getMonthDollarPrice() {
        val flow = flow {
            emit(getMonthDollarPriceUseCase())
        }.flowOn(Dispatchers.IO)

        viewModelScope.launch {
            flow.collectLatest {
                _dollarFlow.value = it
            }
        }
    }

    fun startDollarWorker(checkPoint: Double) {
        viewModelScope.launch {
            startDollarWorkerUseCase(checkPoint)
        }
    }

    init {
        getMonthDollarPrice()
    }
}