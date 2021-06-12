package com.christopher_elias.myapplication.mvi_core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

abstract class CoroutinePresenter<UiModel : Any, UiEvent : Any> {
  private lateinit var model: UiModel
  private val _models = MutableSharedFlow<UiModel>()
  val models: Flow<UiModel>
    get() = _models
      .onStart { onStart(this) }
      // TODO(Benoit) I thought about doing this inside `sendModel` but this get skipped when models
      //  are emitted inside `onStart`.
      .onEach { model = it }

  protected fun getModel(): UiModel {
    return model
  }

  // TODO(Benoit) Do we wanna expose a non-suspend method too? e.g. `trySendModel(model): Boolean`
  protected suspend fun sendModel(model: UiModel) {
    _models.emit(model)
  }

  open suspend fun onStart(collector: FlowCollector<UiModel>) {}
  abstract fun CoroutineScope.handleIntent(intent: UiEvent)
}