package com.christopher_elias.myapplication.mvi_core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

abstract class CoroutineViewModel<UiModel : Any, UiEvent : Any> : ViewModel() {
  abstract val models: Flow<UiModel>
  abstract fun CoroutineScope.handleEvent(event: UiEvent)
}