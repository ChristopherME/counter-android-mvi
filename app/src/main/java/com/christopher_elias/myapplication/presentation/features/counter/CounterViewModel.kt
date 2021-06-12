package com.christopher_elias.myapplication.presentation.features.counter

import com.christopher_elias.myapplication.mvi_core.CoroutineViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

/*
 * Created by Christopher Elias on 24/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

/**
 * This view is responsible of taking care of the presenter lifecycle, more than any logic. It only
 * serves as a proxy of the business logic. It's also because `CoroutinePresenter` is also an
 * abstract class.
 */
@FlowPreview
@ExperimentalCoroutinesApi
class CounterViewModel(
    private val counterPresenter: CounterPresenter,
) : CoroutineViewModel<CounterUiState, CounterIntent>() {
    override val models: Flow<CounterUiState>
        get() = counterPresenter.models

    override fun CoroutineScope.handleEvent(event: CounterIntent) {
        with(counterPresenter) {
            handleIntent(event)
        }
    }
}

