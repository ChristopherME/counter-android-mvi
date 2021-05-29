package com.christopher_elias.myapplication.presentation.features.counter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.christopher_elias.myapplication.mvi_core.MviViewModel
import com.christopher_elias.myapplication.utils.toOneTimeEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/*
 * Created by Christopher Elias on 24/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

@FlowPreview
@ExperimentalCoroutinesApi
class CounterViewModel(
    private val processorHolder: CounterProcessorHolder
) : ViewModel(), MviViewModel<CounterIntent, CounterAction, CounterUiState> {

    init {
        Log.i(this::class.java.simpleName, "Hello!")

        // Trigger the initial intent only once.
        processIntents(CounterIntent.InitialIntent)
        // Subscribe to Actions
        subscribeActions()
    }

    private val _actions = Channel<CounterAction>()
    val actions = _actions.receiveAsFlow()

    private val _uiState = MutableStateFlow(CounterUiState())

    override val uiState: StateFlow<CounterUiState>
        get() = _uiState.asStateFlow()

    override fun processIntents(intent: CounterIntent) {
        viewModelScope.launch {
            _actions.send(mapIntentToAction(intent = intent))
        }
    }

    override fun mapIntentToAction(intent: CounterIntent): CounterAction {
        Log.i(this::class.java.simpleName, "mapIntentToAction: ${intent::class.java.simpleName}!")
        return when (intent) {
            CounterIntent.InitialIntent -> CounterAction.LoadCurrentCount
            CounterIntent.IncrementCounterIntent -> CounterAction.IncrementCount
        }
    }

    private fun subscribeActions() {
        viewModelScope.launch {
            actions
                .flatMapLatest { processorHolder.processAction(action = it) }
                .collectLatest { reduce(it) }
        }
    }

    private fun reduce(result: CounterResult) {
        Log.i(this::class.java.simpleName, "reduce: $result")
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

    override fun onCleared() {
        Log.i(this::class.java.simpleName, "Bye!")
        super.onCleared()
    }
}

