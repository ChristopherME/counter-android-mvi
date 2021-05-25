package com.christopher_elias.myapplication.utils

import com.christopher_elias.myapplication.presentation.features.counter.CounterIntent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

/*
 * Created by Christopher Elias on 24/05/2021
 * christopher.elias@loop-ideas.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

fun Flow<CounterIntent>.takeOnce(): Flow<CounterIntent> {
    var initialIntent: CounterIntent.InitialIntent? = null
    return transform { value ->

        if (initialIntent == null) {
            if (value is CounterIntent.InitialIntent) {
                initialIntent = value
            }
        }
        if (value is CounterIntent.InitialIntent) {


            return@transform emit(value)
        }
    }
}