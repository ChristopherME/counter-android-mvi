package com.christopher_elias.myapplication.presentation.features.counter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.christopher_elias.myapplication.R
import com.christopher_elias.myapplication.databinding.FragmentCounterBinding
import com.christopher_elias.myapplication.mvi_core.MviView
import com.christopher_elias.myapplication.presentation.base.LifecycleLoggerFragment
import com.christopher_elias.myapplication.presentation.features.FragmentB
import com.christopher_elias.myapplication.utils.clicks
import com.christopher_elias.myapplication.utils.consumeOnce
import com.christopher_elias.myapplication.navigation_ext.replaceFragmentExt
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/*
 * Created by Christopher Elias on 16/05/2021
 * christopher.mike.96@gmail.com
 *
 * Loop Ideas
 * Lima, Peru.
 */
@FlowPreview
@ExperimentalCoroutinesApi
class CounterFragment : LifecycleLoggerFragment(R.layout.fragment_counter),
    MviView<CounterIntent, CounterUiState> {

    private val binding by viewBinding(FragmentCounterBinding::bind)

    /* Even though the CounterViewModel can survive config changes & replace fragment (when is added to the backStack)
     * transactions, the CounterViewModelFactory() will get called everytime this fragment is available after those operations again.
     * Therefore, all the dependencies created in CounterViewModelFactory, will be re-created and "living" in memory doing nothing...
     *
     * It is NOT a memory leak, but it's bad for performance due the extra instances that are not used by any class.
     */
    private val viewModel by viewModels<CounterViewModel> { CounterViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectUiState()
        processIntents()
        navigation()
    }

    private fun processIntents() {
        intents()
            .onEach { intent -> viewModel.processIntents(intent) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { render(it) }
        }
    }

    private fun navigation() {
        binding.tvFragmentAtoB.setOnClickListener {
            replaceFragmentExt(
                newFragment = FragmentB(),
                addToBackStack = true,
                fromActivity = true
            )
        }
    }

    override fun intents(): Flow<CounterIntent> {
        val flowIntents = listOf(
            binding.btnIncrease
                .clicks()
                .map { CounterIntent.IncrementCounterIntent }
        )
        return flowIntents.asFlow().flattenMerge(flowIntents.size)
    }

    override fun render(state: CounterUiState) {
        Log.i(this::class.java.simpleName, "render with State: $state")
        with(state) {
            binding.progressCounter.isVisible = isLoading

            binding.tvCurrentValue.isVisible = !isLoading
            binding.tvCurrentValue.text = getString(R.string.tv_current_count, count)

            binding.tvError.isVisible = !isLoading && error != null

            error?.let {
                it.consumeOnce {
                    Toast.makeText(
                        requireContext(),
                        error.payload?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}