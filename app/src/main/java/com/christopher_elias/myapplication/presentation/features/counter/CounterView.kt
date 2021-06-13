package com.christopher_elias.myapplication.presentation.features.counter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.christopher_elias.myapplication.R
import com.christopher_elias.myapplication.databinding.ViewCounterBinding
import com.christopher_elias.myapplication.mvi_core.BaseView
import com.christopher_elias.myapplication.mvi_core.BaseView.EventReceiver
import com.christopher_elias.myapplication.presentation.features.counter.CounterIntent.IncrementCounterIntent
import com.christopher_elias.myapplication.utils.consumeOnce

class CounterView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs), BaseView<CounterUiState, CounterIntent> {
    val binding by lazy { ViewCounterBinding.inflate(LayoutInflater.from(context), this, true) }

    override fun setEventReceiver(receiver: EventReceiver<CounterIntent>) {
        binding.btnIncrease.setOnClickListener { receiver.sendEvent(IncrementCounterIntent) }
    }

    override fun setModel(model: CounterUiState) {
        with(model) {
            binding.progressCounter.isVisible = isLoading

            binding.tvCurrentValue.isVisible = !isLoading
            binding.tvCurrentValue.text = context.getString(R.string.tv_current_count, count)

            binding.tvError.isVisible = !isLoading && error != null

            error?.let {
                it.consumeOnce {
                    android.widget.Toast.makeText(
                        context,
                        error.payload?.message,
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}