package com.christopher_elias.myapplication.presentation.features.counter

import com.christopher_elias.myapplication.mvi_core.MviResult
import com.christopher_elias.myapplication.CounterException

/*
 * Created by Christopher Elias on 24/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

sealed class CounterResult : MviResult {
    data class Success(val currentCount: Int) : CounterResult()
    data class Error(val error: CounterException) : CounterResult()
    object Loading : CounterResult()

}