package com.christopher_elias.myapplication.data

import android.util.Log
import arrow.core.Either
import com.christopher_elias.myapplication.domain.CounterRepository
import com.christopher_elias.myapplication.CounterException

/*
 * Created by Christopher Elias on 24/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

class CounterRepositoryImpl(
    private val remoteDataSource: CounterRemoteDataSource
) : CounterRepository {

    init {
        Log.i(this::class.java.simpleName, "Hello! ${this}~")
    }

    override suspend fun increment(): Either<CounterException, Int> {
        return remoteDataSource.increment()
    }

    override suspend fun currentCount(): Either<CounterException, Int> {
        return remoteDataSource.currentCount()
    }
}