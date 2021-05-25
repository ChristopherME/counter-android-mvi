package com.christopher_elias.myapplication.presentation.features.counter

import com.christopher_elias.myapplication.mvi_core.MviAction

/*
 * Created by Christopher Elias on 24/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

sealed class CounterAction : MviAction {
    object LoadCurrentCount : CounterAction()
    object IncrementCount : CounterAction()
}