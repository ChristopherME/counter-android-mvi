package com.christopher_elias.myapplication.mvi_core

interface BaseView<UiModel : Any, UiEvent : Any> {
  fun interface EventReceiver<UiEvent> {
    fun sendEvent(event: UiEvent)
  }

  fun setEventReceiver(receiver: EventReceiver<UiEvent>)
  fun setModel(model: UiModel)
}