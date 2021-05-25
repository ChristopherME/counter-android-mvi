package com.christopher_elias.myapplication.presentation.features

import android.os.Bundle
import android.view.View
import com.christopher_elias.myapplication.R
import com.christopher_elias.myapplication.databinding.FragmentCBinding
import com.christopher_elias.myapplication.presentation.base.LifecycleLoggerFragment
import com.christopher_elias.myapplication.utils.pop
import com.christopher_elias.myapplication.utils.popUntil
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

/*
 * Created by Christopher Elias on 16/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */

class FragmentC : LifecycleLoggerFragment(R.layout.fragment_c) {

    private val binding by viewBinding(FragmentCBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvFragmentCtoA.setOnClickListener {
            popUntil(fragmentTag = "FragmentA")
        }

        binding.tvFragmentCtoB.setOnClickListener {
            pop()
        }
    }
}