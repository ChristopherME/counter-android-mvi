package com.christopher_elias.myapplication.presentation.features

import android.os.Bundle
import android.view.View
import com.christopher_elias.myapplication.R
import com.christopher_elias.myapplication.databinding.FragmentBBinding
import com.christopher_elias.myapplication.presentation.base.LifecycleLoggerFragment
import com.christopher_elias.myapplication.utils.replaceFragmentExt
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

/*
 * Created by Christopher Elias on 16/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

class FragmentB : LifecycleLoggerFragment(R.layout.fragment_b) {

    private val binding by viewBinding(FragmentBBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvFragmentBtoC.setOnClickListener {
            replaceFragmentExt(
                newFragment = FragmentC(),
                addToBackStack = true,
                fromActivity = true
            )
        }
    }
}