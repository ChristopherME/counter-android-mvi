package com.christopher_elias.myapplication.presentation.features.counter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.christopher_elias.myapplication.R
import com.christopher_elias.myapplication.databinding.FragmentCounterBinding
import com.christopher_elias.myapplication.mvi_core.BaseView.EventReceiver
import com.christopher_elias.myapplication.navigation_ext.replaceFragmentExt
import com.christopher_elias.myapplication.presentation.base.LifecycleLoggerFragment
import com.christopher_elias.myapplication.presentation.features.FragmentB
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
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
    EventReceiver<CounterIntent> {

    private val binding by viewBinding(FragmentCounterBinding::bind)

    private var presenterBinding: Pair<CoroutineScope, Job>? = null

    /* Even though the CounterViewModel can survive config changes & replace fragment (when is added to the backStack)
     * transactions, the CounterViewModelFactory() will get called everytime this fragment is available after those operations again.
     * Therefore, all the dependencies created in CounterViewModelFactory, will be re-created and "living" in memory doing nothing...
     *
     * It is NOT a memory leak, but it's bad for performance due the extra instances that are not used by any class.
     */
    private val viewModel by viewModels<CounterViewModel> { CounterViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationListener()

        presenterBinding = start { binding.counterView.setModel(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenterBinding?.second?.cancel()
    }

    override fun sendEvent(event: CounterIntent) {
        with(viewModel) {
            presenterBinding!!.first.handleEvent(event)
        }
    }

    /**
     * IMHO navigation should be outside of the MVI Flow.
     * That's why this is not consider an "intent".
     * Same.
     */
    // TODO(Benoit) I think that it should live in the presenter: https://code.cash.app/android-presenters
    private fun navigationListener() {
        binding.counterView.binding.tvFragmentAtoB.setOnClickListener {
            replaceFragmentExt(
                newFragment = FragmentB(),
                addToBackStack = true,
                fromActivity = true
            )
        }
    }

    private fun start(models: (CounterUiState) -> Unit): Pair<CoroutineScope, Job> {
        val job = Job()
        val scope = CoroutineScope(viewLifecycleOwner.lifecycleScope.coroutineContext + job)
        scope.launch {
            viewModel.models.collect { models(it) }
        }
        return scope to job
    }
}