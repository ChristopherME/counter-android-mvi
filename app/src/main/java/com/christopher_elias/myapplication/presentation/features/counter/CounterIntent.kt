package com.christopher_elias.myapplication.presentation.features.counter

import com.christopher_elias.myapplication.mvi_core.MviIntent

/*
 * Created by Christopher Elias on 24/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

sealed class CounterIntent : MviIntent {

    /**
     * When the user enters the screen the firs thing to do is load the counter.
     */
    object InitialIntent : CounterIntent()

    /**
     * When the user "clicks" the increment button.
     */
    object IncrementCounterIntent : CounterIntent()
}