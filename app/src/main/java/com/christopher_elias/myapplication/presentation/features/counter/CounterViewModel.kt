package com.christopher_elias.myapplication.presentation.features.counter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.christopher_elias.myapplication.data.CounterRemoteDataSource
import com.christopher_elias.myapplication.data.CounterRepositoryImpl
import com.christopher_elias.myapplication.data_source.CounterRemoteDataSourceImpl
import com.christopher_elias.myapplication.domain.CounterRepository
import com.christopher_elias.myapplication.mvi_core.MviViewModel
import com.christopher_elias.myapplication.utils.toOneTimeEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/*
 * Created by Christopher Elias on 24/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

class CounterViewModel(
    private val processorHolder: CounterProcessorHolder
) : ViewModel(), MviViewModel<CounterIntent, CounterAction, CounterUiState> {

    private val _uiState = MutableStateFlow(CounterUiState())

    override val uiState: StateFlow<CounterUiState>
        get() = _uiState.asStateFlow()

    override fun processIntents(intents: Flow<CounterIntent>) {
        viewModelScope.launch {
            intents
                .onEach { Log.d("CounterViewModel", "$it") }
                .map { intent -> mapIntentToAction(intent) }
                .flatMapLatest { action -> processorHolder.processAction(action) }
                .collect { result -> reduce(result) }
        }
    }

    override fun mapIntentToAction(intent: CounterIntent): CounterAction {
        return when (intent) {
            CounterIntent.InitialIntent -> CounterAction.LoadCurrentCount
            CounterIntent.IncrementCounterIntent -> CounterAction.IncrementCount
        }
    }

    private fun reduce(result: CounterResult) {
        when (result) {
            CounterResult.Loading -> _uiState.value = uiState.value.copy(isLoading = true)
            is CounterResult.Error -> {
                _uiState.value =
                    uiState.value.copy(error = result.error.toOneTimeEvent(), isLoading = false)
            }

            is CounterResult.Success -> {
                _uiState.value =
                    uiState.value.copy(isLoading = false, count = result.currentCount, error = null)
            }
        }
    }
}

class CounterViewModelFactory : ViewModelProvider.Factory {
    private val remoteDs: CounterRemoteDataSource = CounterRemoteDataSourceImpl(
        defaultDispatcher = Dispatchers.Default
    )

    private val repo: CounterRepository = CounterRepositoryImpl(remoteDs)

    private val processorHolder = CounterProcessorHolder(repo)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CounterViewModel::class.java)) {
            return CounterViewModel(processorHolder) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}