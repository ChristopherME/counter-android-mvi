package com.christopher_elias.myapplication.presentation.features.counter

import arrow.core.Either
import com.christopher_elias.myapplication.domain.CounterRepository
import com.christopher_elias.myapplication.mvi_core.MviProcessorHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*
 * Created by Christopher Elias on 24/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

class CounterProcessorHolder(
    private val repository: CounterRepository
) : MviProcessorHolder<CounterAction, CounterResult> {

    override fun processAction(action: CounterAction): Flow<CounterResult> {
        return flow {
            emit(CounterResult.Loading)
            when (action) {
                CounterAction.IncrementCount -> {
                    when (val result = repository.increment()) {
                        is Either.Left -> emit(CounterResult.Error(error = result.value))
                        is Either.Right -> emit(CounterResult.Success(currentCount = result.value))
                    }
                }
                CounterAction.LoadCurrentCount -> {
                    when (val result = repository.currentCount()) {
                        is Either.Left -> emit(CounterResult.Error(error = result.value))
                        is Either.Right -> emit(CounterResult.Success(currentCount = result.value))
                    }
                }
            }
        }
    }
}