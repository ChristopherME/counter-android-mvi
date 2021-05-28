package com.christopher_elias.myapplication.presentation.features.home

import android.os.Bundle
import android.util.Log
import android.view.View
import com.christopher_elias.myapplication.R
import com.christopher_elias.myapplication.presentation.base.LifecycleLoggerFragment
import com.christopher_elias.myapplication.presentation.features.counter.CounterFragment
import com.christopher_elias.myapplication.utils.addFragmentExt

/*
 * Created by Christopher Elias on 16/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

class HomeFragment : LifecycleLoggerFragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(
            this::class.java.simpleName,
            "Fragments size [Begin]: ${childFragmentManager.fragments.size}"
        )
        Log.i(
            this::class.java.simpleName,
            "SavedInstance: $savedInstanceState"
        )
        addFragmentExt(
            newFragment = CounterFragment(),
            addToBackStack = false,
            fromActivity = false,
            containerId = R.id.innerFragmentContainer
        )
        Log.d(
            this::class.java.simpleName,
            "Fragments size [End]: ${childFragmentManager.fragments.size}"
        )
    }
}