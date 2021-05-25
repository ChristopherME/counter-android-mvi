package com.christopher_elias.myapplication.data

import arrow.core.Either
import com.christopher_elias.myapplication.CounterException

/*
 * Created by Christopher Elias on 24/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

interface CounterRemoteDataSource {

    /**
     * Simulates the request for increment something to some fake remote data source.
     */
    suspend fun increment(): Either<CounterException, Int>

    /**
     * Simulates the request of get some counter value from some fake remote data source.
     */
    suspend fun currentCount(): Either<CounterException, Int>
}