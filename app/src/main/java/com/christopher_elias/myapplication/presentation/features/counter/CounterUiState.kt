package com.christopher_elias.myapplication.presentation.features.counter

import com.christopher_elias.myapplication.CounterException
import com.christopher_elias.myapplication.mvi_core.MviViewState
import com.christopher_elias.myapplication.utils.OneTimeEvent

/*
 * Created by Christopher Elias on 24/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

/**
 * Represent the UI State Model for [CounterFragment]
 */
data class CounterUiState(
    val count: Int = 0,
    val isLoading: Boolean = false,
    val error: OneTimeEvent<CounterException>? = null
) : MviViewState