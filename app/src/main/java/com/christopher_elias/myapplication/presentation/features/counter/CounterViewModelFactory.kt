package com.christopher_elias.myapplication.presentation.features.counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.christopher_elias.myapplication.data.CounterRemoteDataSource
import com.christopher_elias.myapplication.data.CounterRepositoryImpl
import com.christopher_elias.myapplication.data_source.CounterRemoteDataSourceImpl
import com.christopher_elias.myapplication.domain.CounterRepository
import kotlinx.coroutines.Dispatchers

/*
 * Created by Christopher Elias on 24/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

class CounterViewModelFactory : ViewModelProvider.Factory {
    private val remoteDs: CounterRemoteDataSource = CounterRemoteDataSourceImpl(
        defaultDispatcher = Dispatchers.Default
    )

    private val repo: CounterRepository = CounterRepositoryImpl(remoteDs)

    private val presenter = CounterPresenter(repo)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CounterViewModel::class.java)) {
            return CounterViewModel(presenter) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}