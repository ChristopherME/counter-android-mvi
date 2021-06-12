package com.christopher_elias.myapplication.presentation.features.counter

import arrow.core.Either.Left
import arrow.core.Either.Right
import com.christopher_elias.myapplication.domain.CounterRepository
import com.christopher_elias.myapplication.mvi_core.CoroutinePresenter
import com.christopher_elias.myapplication.presentation.features.counter.CounterIntent.IncrementCounterIntent
import com.christopher_elias.myapplication.utils.toOneTimeEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

class CounterPresenter(
  private val repository: CounterRepository,
) : CoroutinePresenter<CounterUiState, CounterIntent>() {
  override suspend fun onStart(collector: FlowCollector<CounterUiState>) {
    val defaultModel = CounterUiState()
    collector.emit(defaultModel.copy(isLoading = true))
    when (val result = repository.currentCount()) {
      is Left -> {
        // Why do we need a OneTimeEvent ?
        collector.emit(defaultModel.copy(error = result.value.toOneTimeEvent(), isLoading = false))
      }
      is Right -> {
        collector.emit(defaultModel.copy(isLoading = false, count = result.value, error = null))
      }
    }
  }

  override fun CoroutineScope.handleIntent(intent: CounterIntent) {
    when (intent) {
      IncrementCounterIntent -> {
        launch {
          sendModel(getModel().copy(isLoading = true))
          when (val result = repository.increment()) {
            is Left -> {
              // Why do we need a OneTimeEvent ?
              sendModel(getModel().copy(error = result.value.toOneTimeEvent(), isLoading = false))
            }
            is Right -> {
              sendModel(getModel().copy(isLoading = false, count = result.value, error = null))
            }
          }
        }
      }
    }
  }
}