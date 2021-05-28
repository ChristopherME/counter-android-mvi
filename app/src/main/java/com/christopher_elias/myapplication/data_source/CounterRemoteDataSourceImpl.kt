package com.christopher_elias.myapplication.data_source

import android.util.Log
import arrow.core.Either
import com.christopher_elias.myapplication.data.CounterRemoteDataSource
import com.christopher_elias.myapplication.CounterException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/*
 * Created by Christopher Elias on 24/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

class CounterRemoteDataSourceImpl(
    private val defaultDispatcher: CoroutineDispatcher
) : CounterRemoteDataSource {

    init {
        Log.i(this::class.java.simpleName, "Hello! ${this}~")
    }

    private var count = 0

    override suspend fun increment(): Either<CounterException, Int> {
        return withContext(defaultDispatcher) {
            delay(1500L)
            if (shouldFail()) {
                Either.Left(CounterException())
            } else {
                count += 1
                Either.Right(count)
            }
        }
    }

    override suspend fun currentCount(): Either<CounterException, Int> {
        return withContext(defaultDispatcher) {
            delay(1500L)
            if (shouldFail()) {
                Either.Left(CounterException())
            } else {
                Either.Right(count)
            }
        }
    }

    /**
     * Helper method for return a "random" failure.
     * @return [Boolean]
     */
    private fun shouldFail(): Boolean = (0..4).random().mod(2) == 0
}