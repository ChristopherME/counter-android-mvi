package com.christopher_elias.myapplication.domain

import arrow.core.Either
import com.christopher_elias.myapplication.CounterException

/*
 * Created by Christopher Elias on 24/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

interface CounterRepository {

    suspend fun increment(): Either<CounterException, Int>

    suspend fun currentCount(): Either<CounterException, Int>

}